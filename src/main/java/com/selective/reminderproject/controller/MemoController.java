package com.selective.reminderproject.controller;

import com.selective.reminderproject.dto.MemoDTO;
import com.selective.reminderproject.dto.MemoTextDTO;
import com.selective.reminderproject.entity.Memo;
import com.selective.reminderproject.entity.MemoText;
import com.selective.reminderproject.entity.User;
import com.selective.reminderproject.service.MemoService;
import com.selective.reminderproject.service.MemoTextService;
import com.selective.reminderproject.service.UserService;
import com.selective.reminderproject.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MemoController {
    private final UserService userService;
    private final MemoService memoService;
    private final MemoTextService memoTextService;

    public MemoController(UserService userService,MemoService memoservice, MemoTextService memoTextService) {
        this.userService = userService;
        this.memoService = memoservice;
        this.memoTextService = memoTextService;
    }

    @PostMapping("/memo/upload")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> UploadMemo(@Valid @RequestBody MemoDTO memoDTO) {
        Optional<String> username = SecurityUtil.getCurrentUsername();
        Optional<User> userOptional = userService.findUserByUsername(username);

        if (userOptional.isPresent()) {
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
}
