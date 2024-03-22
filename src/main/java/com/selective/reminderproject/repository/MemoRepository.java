package com.selective.reminderproject.repository;

import com.selective.reminderproject.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByUserUsername(String username);

    @Override
    Optional<Memo> findById(Long aLong);


}
