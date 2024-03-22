package com.selective.reminderproject.service;

import com.selective.reminderproject.dto.MemoDTO;
import com.selective.reminderproject.dto.MemoTextDTO;
import com.selective.reminderproject.entity.Memo;
import com.selective.reminderproject.entity.MemoText;
import com.selective.reminderproject.repository.MemoRepository;
import com.selective.reminderproject.repository.MemoTextRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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

    private MemoDTO convertToDto(Memo memo) {
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.setMemoId(memo.getMemoId());
        memoDTO.setCreateyear(memo.getCreateyear());
        memoDTO.setCreatemonth(memo.getCreatemonth());
        memoDTO.setCreateday(memo.getCreateday());
        memoDTO.setCreatehour(memo.getCreatehour());
        memoDTO.setCreateminute(memo.getCreateminute());
        memoDTO.setCreateweek(memo.getCreateweek());
        memoDTO.setTitle(memo.getTitle());
        memoDTO.setFeeling(memo.getFeeling());

        // MemoTextDTO로 변환
        List<MemoTextDTO> memoTextDTOs = new ArrayList<>();
        if (memo.getMemoTexts() != null) {
            for (MemoText memoText : memo.getMemoTexts()) {
                MemoTextDTO memoTextDTO = new MemoTextDTO();
                memoTextDTO.setMemoTextId(memoText.getMemoTextId());
                memoTextDTO.setMemoId(memoText.getMemo().getMemoId());
                memoTextDTO.setContent(memoText.getContent());
                memoTextDTO.set_do(memoText.is_do());
                memoTextDTOs.add(memoTextDTO);
            }
        }
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
}
