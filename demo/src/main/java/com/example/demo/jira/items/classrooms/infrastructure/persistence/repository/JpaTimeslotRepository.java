package com.example.demo.jira.items.classrooms.infrastructure.persistence.repository;

import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTimeslotRepository extends JpaRepository<TimeSlotEntity,Long> {
}
