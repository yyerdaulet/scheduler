package com.example.demo.jira.items.subject.repository;

import com.example.demo.jira.items.subject.enties.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity,Long> {
}
