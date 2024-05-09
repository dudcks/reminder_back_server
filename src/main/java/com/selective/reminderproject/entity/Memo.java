package com.selective.reminderproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "memo", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @Column(name = "memo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",nullable = false)
    private User user;

    @Column(name = "create_year")
    private short createyear;

    @Column(name = "create_month")
    private short createmonth;

    @Column(name = "create_day")
    private short createday;

    @Column(name = "title")
    private String title;

    @Column(name = "feeling")
    private String feeling;

    @OneToMany(mappedBy = "memo", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<MemoText> memotext;

    public List<MemoText> getMemoTexts() {
        return this.memotext;
    }

}
