package com.selective.reminderproject.service;

import com.selective.reminderproject.dto.MemoDTO;
import com.selective.reminderproject.dto.MemoTextDTO;
import com.selective.reminderproject.entity.Memo;
import com.selective.reminderproject.entity.MemoText;
import com.selective.reminderproject.repository.MemoRepository;
import com.selective.reminderproject.repository.MemoTextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemoService {

    private MemoRepository memoRepository = null;
    private MemoTextRepository memoTextRepository=null;

    public MemoService(MemoRepository memoRepository, MemoTextRepository memoTextRepository) {
        this.memoRepository = memoRepository;
        this.memoTextRepository = memoTextRepository;
    }

    @Transactional
    public Memo save(Memo memo) {
        return memoRepository.save(memo);
    }

    public List<MemoDTO> getAllMemosByUsername(String username) {
        List<Memo> memos = memoRepository.findByUserUsername(username);
        return memos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MemoDTO> getAllMemosByUsername_by_last(Long Id, Long last) {
        List<Memo> memos=null;
        if(last<0){
            memos = memoRepository.findByUserUsername_by_last(Id,2);
        }
        else
            memos = memoRepository.findByUserUsername_by_last(Id,last,2);
        return memos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public String getAllMemocontentByUsername(Long user_id) {
        List<Memo> memos = memoRepository.findByUserUsername_by_num(user_id,100);
        StringBuilder all_text= new StringBuilder();
        int i=0;
        for(Memo memo: memos) {
            all_text.append(getMemoText(memo));
            all_text.append(" ");
        }
        return all_text.toString();
    }

    private MemoDTO convertToDto(Memo memo) {
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.setMemoId(memo.getMemoId());
        memoDTO.setCreateyear(memo.getCreateyear());
        memoDTO.setCreatemonth(memo.getCreatemonth());
        memoDTO.setCreateday(memo.getCreateday());
        memoDTO.setTitle(memo.getTitle());
        memoDTO.setFeeling(memo.getFeeling());

        // MemoTextDTO로 변환
        List<MemoTextDTO> memoTextDTOs = getMemoTextDTOS(memo);
        memoDTO.setMemoTexts(memoTextDTOs);

        return memoDTO;
    }

    @Transactional(readOnly = true)
    public Optional<Memo> findMemoById(Long memoId) {
        return memoRepository.findById(memoId);
    }

    @Transactional
    public void deleteMemoAndTexts(Long memoId) {
        Optional<Memo> memoOptional = memoRepository.findById(memoId);
        if (memoOptional.isPresent()) {
            Memo memo = memoOptional.get();
            memoRepository.delete(memo);
        }
    }

    @Transactional(readOnly = true)
    public MemoDTO getTodayMemosByUsernameAndDate(String username, short yaer, short month, short day){
        Memo todaymemo = memoRepository.findTodayMemosByUsernameAndDate(username,yaer,month,day);

        try {
            List<MemoTextDTO> memoTextDTOs = getMemoTextDTOS(todaymemo);

            return MemoDTO.builder()
                    .memoId(todaymemo.getMemoId())
                    .createyear(todaymemo.getCreateyear())
                    .createmonth(todaymemo.getCreatemonth())
                    .createday(todaymemo.getCreateday())
                    .title(todaymemo.getTitle())
                    .feeling(todaymemo.getFeeling())
                    .memoTexts(memoTextDTOs)
                    .build();
        }
        catch(Exception ex){
            return null;
        }
    }

    private static List<MemoTextDTO> getMemoTextDTOS(Memo memo) {
        List<MemoTextDTO> memoTextDTOs = new ArrayList<>();
        if (memo.getMemoTexts() != null) {
            for (MemoText memoText : memo.getMemoTexts()) {
                MemoTextDTO memoTextDTO = new MemoTextDTO();
                memoTextDTO.setMemoTextId(memoText.getMemoTextId());
                memoTextDTO.setMemoId(memoText.getMemo().getMemoId());
                memoTextDTO.setContent(memoText.getContent());
                memoTextDTO.set_do(memoText.get_do());
                memoTextDTO.setAlarm_hour(memoText.getAlarm_hour());
                memoTextDTO.setAlarm_minute(memoText.getAlarm_minute());
                memoTextDTOs.add(memoTextDTO);
            }
        }
        return memoTextDTOs;
    }

    private static String getMemoText(Memo memo) {
        StringBuilder a = new StringBuilder();
        if (memo.getMemoTexts() != null) {
            for (MemoText memoText : memo.getMemoTexts()) {
                a.append(memoText.getContent());
                a.append(" ");
            }
        }
        return a.toString();
    }
}
