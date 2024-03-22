package com.selective.reminderproject.controller;

import com.selective.reminderproject.dto.PostDto;
import com.selective.reminderproject.entity.Post;
import com.selective.reminderproject.repository.PostRepository;
import com.selective.reminderproject.service.UserService;
import com.selective.reminderproject.service.postService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.selective.reminderproject.service.postService.summitPostLike;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostRepository postRepository;

    private final UserService userService;

    @Autowired
    public PostController(PostRepository postRepository,UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @PostMapping("/upload_post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity uploadPost(@Valid @RequestBody PostDto postDto) {
        try {
            Post post = postDto.toEntity();
            Post savedPost = postRepository.save(post);
            String username = postDto.getUsername();

            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            e.printStackTrace(); // 에러를 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/load_post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Post> loadPost(@RequestParam(name = "last", defaultValue = "-1") Long last,
                               @RequestParam(name = "username", required = true) String username){
        if (last == -1) {
            List<Post> posts = postService.getLatestPosts(username,6);
            return posts;
        } else {
            List<Post> posts = postService.getLatestPosts(username,6,last);
            return posts;
        }
    }
    @GetMapping("/my_post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Post> loadMyPost(
            @RequestParam(name = "last", defaultValue = "-1")Long last,
            @RequestParam(name = "username", required = true) String username){

        if (last == -1) {
            List<Post> posts = postService.getMyPosts(username);
            return posts;
        } else {
            List<Post> posts = postService.getMyPosts(username,last);
            return posts;
        }
    }

    @GetMapping("/post_like")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity PostLike(@RequestParam(name = "id", required = true) long post_id) {
        try {
            summitPostLike(post_id);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        } catch (Exception e) {
            e.printStackTrace(); // 에러를 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/search_post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Post> searchPost(@RequestParam(name ="search")String search,
                                 @RequestParam(name ="last", defaultValue = "-1")Long last,
                                 @RequestParam(name ="username")String username){
        if (last == -1) {
            List<Post> posts = postService.findPostsByTextContaining(search,username);
            return posts;
        } else {
            List<Post> posts = postService.findPostsByTextContaining(search,last,username);
            return posts;
        }
    }
}