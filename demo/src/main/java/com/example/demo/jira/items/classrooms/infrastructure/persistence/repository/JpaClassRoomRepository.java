package com.example.demo.jira.items.classrooms.infrastructure.persistence.repository;

import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.ClassRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClassRoomRepository extends JpaRepository<ClassRoomEntity,Long> {
}
