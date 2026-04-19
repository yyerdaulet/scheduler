package com.example.demo.jira.items.subject.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSubjectRepository extends JpaRepository<SubjectEntity,Long> {
}
