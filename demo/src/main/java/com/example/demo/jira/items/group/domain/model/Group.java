package com.example.demo.jira.items.group.domain.model;

import com.example.demo.jira.items.group.enums.Direction;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Group {
    private Long id;
    private String name;
    private Long enrolledYear;
    private Direction direction;

    public Group(Long id,String name,Long enrolledYear,Direction direction){
        validateEnrolledYear(enrolledYear);
        validateDirection(enrolledYear,direction);

        this.id = id;
        this.name = name;
        this.enrolledYear = enrolledYear;
        this.direction = direction;
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


