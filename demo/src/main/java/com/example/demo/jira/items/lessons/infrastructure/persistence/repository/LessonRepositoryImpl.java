package com.example.demo.jira.items.lessons.infrastructure.persistence.repository;

import com.example.demo.jira.items.lessons.domain.model.Lesson;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.LessonEntity;
import com.example.demo.jira.items.lessons.infrastructure.persistence.mapper.LessonMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Lesson> findById(Long id) {
        return Optional.ofNullable(
                mapper.toDomain(   jpa.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Lesson Not Found")
                        ))
        );
    }
}
