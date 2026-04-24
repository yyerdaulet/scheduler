package com.example.demo.jira.items.group.domain.model;

import com.example.demo.jira.items.group.enums.Direction;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Group {
    private Long id;
    private String name;
    private Long enrolledYear;
    private Direction direction;
    private List<Subgroup> subgroups;

    public Group(Long id,String name,Long enrolledYear,Direction direction,List<Subgroup> subgroups){
        validateEnrolledYear(enrolledYear);
        validateDirection(enrolledYear,direction);

        this.id = id;
        this.name = name;
        this.enrolledYear = enrolledYear;
        this.direction = direction;
        this.subgroups = subgroups;
    }

    public void addSubgroups(List<Subgroup> subgroup){
        this.subgroups.addAll(subgroup);
    }

    private void validateEnrolledYear(Long enrolled_year) {
        Long currentYear = (long) LocalDate.now().getYear();
        if(currentYear - enrolled_year >= 4){
            throw new IllegalArgumentException("This group near to graduate or has been already graduated");
        }
    }

    private void validateDirection(Long enrolledYear,Direction direction){
        Long currentYear = (long) LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonth().getValue();

        if(currentYear - enrolledYear > 2 && direction == Direction.GENERAL){
            throw new IllegalArgumentException("Wrong Direction chosen");
        }

        if(currentYear - enrolledYear < 3 && currentMonth < 5 && direction != Direction.GENERAL){
            throw new IllegalArgumentException("Too early to choose Direction");
        }
    }


}


