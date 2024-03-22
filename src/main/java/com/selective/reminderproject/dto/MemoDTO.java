package com.selective.reminderproject.dto;

import com.selective.reminderproject.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoDTO {
    private Long memoId;
    private User user;
    private short createyear;
    private short createmonth;
    private short createday;
    private short createhour;
    private short createminute;
    private String createweek;
    private String title;
    private String feeling;
    private List<MemoTextDTO> memoTexts;
}
