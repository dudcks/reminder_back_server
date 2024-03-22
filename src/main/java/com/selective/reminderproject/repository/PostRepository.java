package com.selective.reminderproject.repository;

import com.selective.reminderproject.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM post WHERE username <> :username ORDER BY post_id DESC LIMIT :count", nativeQuery = true)
    List<Post> findTopNByOrderByIdDesc(String username,int count);

    @Query(value = "SELECT * FROM post WHERE post_id <= :last AND username <> :username ORDER BY post_id DESC LIMIT :count", nativeQuery = true)
    List<Post> findTopNByOrderByIdDesclast(String username,Long last, int count);

    @Query(value = "SELECT * FROM post WHERE username = :username ORDER BY post_id DESC LIMIT :count", nativeQuery = true)
    List<Post> findTopNByOrderById(String username,int count);

    @Query(value = "SELECT * FROM post WHERE post_id <= :last AND username = :username ORDER BY post_id DESC LIMIT :count", nativeQuery = true)
    List<Post> findTopNByOrderById(String username,Long last, int count);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.postId = :postId")
    void setPostLike(Long postId);

    @Query(value = "SELECT * FROM post WHERE post_id <= :last AND username <> :username AND explain LIKE %:searchText% ORDER BY post_id DESC LIMIT 6", nativeQuery = true)
    List<Post> findPostsByCriteria(String searchText, Long last, String username);

    @Query(value = "SELECT * FROM post WHERE username <> :username AND explain LIKE %:searchText% ORDER BY post_id DESC LIMIT 6", nativeQuery = true)
    List<Post>findPostsByCriteria2(String searchText,String username);
}
