package com.selective.reminderproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "memo", indexes = {
        @Index(name = "idx_username", columnList = "username")
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


    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",nullable = false)
    private User user;

    @Column(name = "createyear")
    private short createyear;

    @Column(name = "createmonth")
    private short createmonth;

    @Column(name = "createday")
    private short createday;

    @Column(name ="createhour")
    private short createhour;

    @Column(name = "createminute")
    private short createminute;

    @Column(name = "createweek")
    private String createweek;

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
