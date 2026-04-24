package com.example.demo.jira.items.profile.dto;

import com.example.demo.jira.items.profile.Enum.Degree;

public record ProfileResponse(Long id,
                              String name,
                              String lastName,
                              java.time.LocalDate birthday,
                              Long hours,
                              Degree degree,
                              String  profileImage,
                              Boolean lectureship) {
}
