package com.selective.reminderproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post", indexes = {@Index(name = "idx_username", columnList = "username")})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "explain", length = 100)
    private String explain;

    @Column(name = "likes")
    private int likes;

    @Lob //큰 데이터
    @Column(name = "image_data", nullable = false)
    private String imageData;

}
