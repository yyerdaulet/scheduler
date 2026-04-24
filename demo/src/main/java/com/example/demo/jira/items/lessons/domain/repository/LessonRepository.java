package com.example.demo.jira.items.lessons.domain.repository;

import com.example.demo.jira.items.lessons.domain.model.Lesson;

import java.util.List;

public interface LessonRepository {
    List<Lesson> saveAll(List<Lesson> lessons);

    List<Lesson> findAll();

    boolean existById(Long lessonId);
}
