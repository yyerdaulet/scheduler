package com.example.demo.jira.items.classrooms.enums;

import lombok.Getter;

@Getter
public enum WeekDay {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    private final int value;

    private WeekDay(int value){
        this.value = value;
    }

}
