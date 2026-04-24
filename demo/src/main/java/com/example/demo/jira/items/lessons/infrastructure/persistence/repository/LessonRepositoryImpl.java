package com.example.demo.jira.items.lessons.infrastructure.persistence.repository;

import com.example.demo.jira.items.lessons.domain.model.Lesson;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.LessonEntity;
import com.example.demo.jira.items.lessons.infrastructure.persistence.mapper.LessonMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LessonRepositoryImpl implements LessonRepository {
    private final JpaLessonRepository jpa;
    private final LessonMapper mapper;

    @Override
    public List<Lesson> saveAll(List<Lesson> lessons) {
        List<LessonEntity> lessonEntities = lessons.stream().map(mapper::toEntity)
                .toList();
        List<LessonEntity> savedLessons = jpa.saveAll(lessonEntities);

        return (List<Lesson>) savedLessons
                .stream()
                .map(mapper::toDomain)
                .toList();
     }

    @Override
    public List<Lesson> findAll() {
        return jpa.findAll()
                .stream().map(
                        mapper::toDomain
                ).toList();
    }

    @Override
    public boolean existById(Long lessonId) {
        return jpa.existsById(lessonId);
    }
}
