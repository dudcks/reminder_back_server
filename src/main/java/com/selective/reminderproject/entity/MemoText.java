package com.selective.reminderproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "memo_text",indexes = {@Index(name = "idx_memo_id", columnList = "memo_id")})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoText {
    @Id
    @Column(name = "memo_text_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoTextId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id",referencedColumnName = "memo_id", nullable = false)
    private Memo memo;

    @Column(name ="is_do")
    private Boolean _do;

    @Column(name = "content")
    private String content;
}
