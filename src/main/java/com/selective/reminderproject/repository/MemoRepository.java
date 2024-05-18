package com.selective.reminderproject.repository;

import com.selective.reminderproject.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByUserUsername(String username);

    @Query(value = "SELECT * FROM Memo m WHERE m.user_id = :username ORDER BY m.memo_id DESC LIMIT :count", nativeQuery = true)
    List<Memo> findByUserUsername_by_num(@Param("username") Long username, @Param("count") int count);

    @Query(value = "SELECT * FROM Memo m WHERE m.user_id = :username AND m.memo_id <= :last ORDER BY m.memo_id DESC LIMIT :count", nativeQuery = true)
    List<Memo> findByUserUsername_by_last(@Param("username") Long username, @Param("last") long last, @Param("count") int count);

    @Query(value = "SELECT * FROM Memo m WHERE m.user_id = :username ORDER BY memo_id DESC LIMIT :count", nativeQuery = true)
    List<Memo> findByUserUsername_by_last(@Param("username") Long username, @Param("count") int count);

    @Override
    Optional<Memo> findById(Long aLong);


    @Query("SELECT m FROM Memo m WHERE m.user.username = :username AND m.createyear = :year AND m.createmonth = :month AND m.createday = :day")
    Memo findTodayMemosByUsernameAndDate(@Param("username") String username, @Param("year") short year, @Param("month") short month, @Param("day") short day);


}
