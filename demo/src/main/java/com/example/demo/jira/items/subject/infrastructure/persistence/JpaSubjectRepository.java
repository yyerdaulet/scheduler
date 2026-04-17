package com.example.demo.jira.items.subject.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSubjectRepository extends JpaRepository<SubjectEntity,Long> {
}
