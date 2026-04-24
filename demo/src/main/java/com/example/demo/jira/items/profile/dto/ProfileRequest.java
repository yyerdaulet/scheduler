package com.example.demo.jira.items.profile.dto;

import com.example.demo.jira.items.profile.Enum.Degree;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record ProfileRequest(
        Long userId,
        String name,
        String lastName,
        LocalDate localDate,
        Long hours,
        Degree degree,
        Boolean lectureship
) {
}
