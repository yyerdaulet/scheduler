package com.example.demo.jira.items.lessons.useCases;

import com.example.demo.jira.items.lessons.domain.model.Lesson;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.lessons.dto.LessonResponse;
import com.example.demo.jira.items.lessons.infrastructure.persistence.mapper.LessonMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllLessons {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;


    public List<LessonResponse> execute(){
        List<Lesson> lessons =  lessonRepository.findAll();
        return lessons
                .stream()
                .map(
                        lessonMapper::toDto
                ).toList();
    }
}
