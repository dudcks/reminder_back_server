package com.selective.reminderproject.controller;

import com.selective.reminderproject.dto.MemoDTO;
import com.selective.reminderproject.dto.MemoTextDTO;
import com.selective.reminderproject.entity.Memo;
import com.selective.reminderproject.entity.MemoText;
import com.selective.reminderproject.entity.User;
import com.selective.reminderproject.service.MemoService;
import com.selective.reminderproject.service.MemoTextService;
import com.selective.reminderproject.service.UserService;
import com.selective.reminderproject.util.KeywordAnalyzer;
import com.selective.reminderproject.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MemoController {
    private final UserService userService;
    private final MemoService memoService;
    private final MemoTextService memoTextService;

    public MemoController(UserService userService, MemoService memoservice, MemoTextService memoTextService, KeywordAnalyzer key) {
        this.userService = userService;
        this.memoService = memoservice;
        this.memoTextService = memoTextService;
    }

    @PostMapping("/memo/upload")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> UploadMemo(@Valid @RequestBody MemoDTO memoDTO) {
        Optional<String> username = SecurityUtil.getCurrentUsername();
        Optional<User> userOptional = userService.findUserByUsername(username);

        //오늘날짜의 메모가 있다면 insert가 아니라 update하는 코드 추가필요<<중요>>
        LocalDate today = LocalDate.now();

        short year = (short) today.getYear();
        short month = (short) today.getMonthValue();
        short day = (short) today.getDayOfMonth();

        if (userOptional.isPresent() && username.isPresent()) {
            User user = userOptional.get();
            String uname = username.get();
            MemoDTO todayMemos = memoService.getTodayMemosByUsernameAndDate(uname, year, month, day);
            if(todayMemos!=null){

                memoDTO.setMemoId(todayMemos.getMemoId());

                Memo memo = Memo.builder()
                        .memoId(memoDTO.getMemoId())
                        .user(user)
                        .createyear(memoDTO.getCreateyear())
                        .createmonth(memoDTO.getCreatemonth())
                        .createday(memoDTO.getCreateday())
                        .title(memoDTO.getTitle())
                        .feeling(memoDTO.getFeeling())
                        .build();

                Memo savedMemo = memoService.save(memo);

                List<MemoTextDTO> memoTextDTOs = todayMemos.getMemoTexts();
                if (memoTextDTOs != null) {
                    for (MemoTextDTO memoTextDTO : memoTextDTOs) {
                        memoTextService.deleteMemoTextById(memoTextDTO.getMemoTextId());// MemoText를 저장하는 서비스 메소드 호출
                    }
                }

                List<MemoTextDTO> memoTextDTOs2 = memoDTO.getMemoTexts();
                if (memoTextDTOs2 != null) {
                    for (MemoTextDTO memoTextDTO2 : memoTextDTOs2) {
                        MemoText memoText = MemoText.builder()
                                .memo(savedMemo)
                                .content(memoTextDTO2.getContent())
                                ._do(memoTextDTO2.get_do())
                                .alarm_hour(memoTextDTO2.getAlarm_hour())
                                .alarm_minute(memoTextDTO2.getAlarm_minute())
                                .build();
                        memoTextService.save(memoText); // MemoText를 저장하는 서비스 메소드 호출
                    }
                }

                return ResponseEntity.ok("오늘 메모 존재,수정");
            }
        }


        if (userOptional.isPresent()) {//메모 추가부분
            User user = userOptional.get();
            Memo memo = Memo.builder()
                    .user(user)
                    .createyear(memoDTO.getCreateyear())
                    .createmonth(memoDTO.getCreatemonth())
                    .createday(memoDTO.getCreateday())
                    .title(memoDTO.getTitle())
                    .feeling(memoDTO.getFeeling())
                    .build();

            Memo savedMemo = memoService.save(memo);

            // MemoText를 저장하는 부분을 여기에 추가해야 합니다.
            List<MemoTextDTO> memoTextDTOs = memoDTO.getMemoTexts();
            if (memoTextDTOs != null) {
                for (MemoTextDTO memoTextDTO : memoTextDTOs) {
                    MemoText memoText = MemoText.builder()
                            .memo(savedMemo)
                            .content(memoTextDTO.getContent())
                            ._do(memoTextDTO.get_do())
                            .alarm_hour(memoTextDTO.getAlarm_hour())
                            .alarm_minute(memoTextDTO.getAlarm_minute())
                            .build();
                    memoTextService.save(memoText); // MemoText를 저장하는 서비스 메소드 호출
                }
            }
            return ResponseEntity.ok("Memo uploaded successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/memo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getAllMemosByUser() {
        Optional<String> usernameOptional = SecurityUtil.getCurrentUsername();
        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();
            List<MemoDTO> memos = memoService.getAllMemosByUsername(username);
            return ResponseEntity.ok(memos);
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/memo/today")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getTodayMemo() {
        LocalDate today = LocalDate.now();

        short year = (short) today.getYear();
        short month = (short) today.getMonthValue();
        short day = (short) today.getDayOfMonth();

        Optional<String> usernameOptional = SecurityUtil.getCurrentUsername();
        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();
            //List<MemoDTO> memos = memoService.getAllMemosByUsername(username);
            MemoDTO todayMemos = memoService.getTodayMemosByUsernameAndDate(username, year, month, day);
            if(todayMemos==null){
                return ResponseEntity.status(400).body("오늘 메모 없음");
            }
            return ResponseEntity.ok(todayMemos);
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @DeleteMapping("/memo/del/{memoId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> deleteMemo(@PathVariable Long memoId) {
        Optional<Memo> memoOptional = memoService.findMemoById(memoId);
        Optional<String> usernameOptional = SecurityUtil.getCurrentUsername();
        if (memoOptional.isPresent()&&usernameOptional.isPresent()) {
            Memo memo = memoOptional.get();
            String username = usernameOptional.get();
            if (memo.getUser().getUsername().equals(username)) {
                memoService.deleteMemoAndTexts(memoId);
                return ResponseEntity.ok("Memo and associated texts deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this memo");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/keyword")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getMyUserkeyword() {
        Optional<String> usernameOptional = SecurityUtil.getCurrentUsername();
        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();
            String memos = memoService.getAllMemocontentByUsername(username);
            //System.out.println(memos);
            Map<String, Integer> keywords = KeywordAnalyzer.keywords(memos);

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : keywords.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                sb.append(key).append(": ").append(value).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
            
            return ResponseEntity.ok(keywords);
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}
