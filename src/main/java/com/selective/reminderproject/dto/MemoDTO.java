package com.selective.reminderproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoDTO {
    private Long memoId;
    private short createyear;
    private short createmonth;
    private short createday;
    private String title;
    private String feeling;
    private List<MemoTextDTO> memoTexts;
}
