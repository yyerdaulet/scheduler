package com.example.demo.jira.items.profile.domain.model;


import com.example.demo.jira.items.profile.Enum.Degree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthday;
    private Long hours;
    private Degree degree;
    private String profileImage;
    private Boolean lectureship;
}
