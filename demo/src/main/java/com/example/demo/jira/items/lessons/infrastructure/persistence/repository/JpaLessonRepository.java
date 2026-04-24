package com.example.demo.jira.items.lessons.infrastructure.persistence.repository;

import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLessonRepository extends JpaRepository<LessonEntity,Long> {
}
