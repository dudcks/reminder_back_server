package com.selective.reminderproject.dto;

import lombok.*;

@Getter
@Setter
public class MemoTextDTO {
    private Long memoTextId;
    private Long memoId;
    private int icon;
    private Boolean _do;
    private String content;
    private short alarm_hour;
    private short alarm_minute;
}
