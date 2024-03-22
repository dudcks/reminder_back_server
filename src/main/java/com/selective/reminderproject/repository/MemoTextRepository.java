package com.selective.reminderproject.repository;

import com.selective.reminderproject.entity.MemoText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MemoTextRepository extends JpaRepository<MemoText,Long> {

}
