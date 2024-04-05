package com.selective.reminderproject.dto;

import lombok.*;

@Getter
@Setter
public class MemoTextDTO {
    private Long memoTextId;
    private Long memoId;
    private Boolean _do;
    private String content;
}
