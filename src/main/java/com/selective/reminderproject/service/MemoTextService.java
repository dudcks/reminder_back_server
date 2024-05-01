package com.selective.reminderproject.service;

import com.selective.reminderproject.entity.MemoText;
import com.selective.reminderproject.repository.MemoTextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemoTextService {
    private static MemoTextRepository memoTextRepository;

    public MemoTextService(MemoTextRepository memoTextRepository){
        this.memoTextRepository=memoTextRepository;
    }

    @Transactional
    public MemoText save(MemoText memoText){return memoTextRepository.save(memoText);}

    public void deleteMemoTextById(Long memoTextId) {
        memoTextRepository.deleteById(memoTextId);
    }

}
