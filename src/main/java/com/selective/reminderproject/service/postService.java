package com.selective.reminderproject.service;

import com.selective.reminderproject.entity.Post;
import com.selective.reminderproject.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class postService {
    private static PostRepository postRepository = null;

    public postService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    @Transactional
    public static List<Post> getLatestPosts(String username,int count) {
        // 여기에서는 JpaRepository 또는 CrudRepository에서 제공하는 메서드를 사용하여 데이터베이스에서 데이터를 가져옵니다.
        // 실제로는 해당 메서드를 데이터베이스에 맞게 구현해야 합니다.
        return postRepository.findTopNByOrderByIdDesc(username,count);
    }

    @Transactional
    public static List<Post> getLatestPosts(String username,int count,Long last) {
        // 여기에서는 JpaRepository 또는 CrudRepository에서 제공하는 메서드를 사용하여 데이터베이스에서 데이터를 가져옵니다.
        // 실제로는 해당 메서드를 데이터베이스에 맞게 구현해야 합니다.
        return postRepository.findTopNByOrderByIdDesclast(username,last,count);
    }


    @Transactional
    public static List<Post> getMyPosts(String username) {

        return postRepository.findTopNByOrderById(username,9);
    }

    @Transactional
    public static List<Post> getMyPosts(String username,Long last) {
        return postRepository.findTopNByOrderById(username,last,9);
    }
    @Transactional
    public static void summitPostLike(long post_id) {
        postRepository.setPostLike(post_id);
    }

    @Transactional
    public static List<Post> findPostsByTextContaining(String searchText,Long id, String username) {
        return postRepository.findPostsByCriteria(searchText,id,username);
    }
    @Transactional
    public static List<Post> findPostsByTextContaining(String searchText,String username) {
        return postRepository.findPostsByCriteria2(searchText,username);
    }
}
