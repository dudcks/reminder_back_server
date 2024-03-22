package com.selective.reminderproject.dto;

import com.selective.reminderproject.entity.Post;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 0, max = 100)
    private String explain;

    @NotNull
    private int likes;

    @NotNull
    private String imageData;

    public Post toEntity() {
        return Post.builder()
                .username(username)
                .explain(explain)
                .likes(likes)
                .imageData(imageData)
                .build();
    }
}