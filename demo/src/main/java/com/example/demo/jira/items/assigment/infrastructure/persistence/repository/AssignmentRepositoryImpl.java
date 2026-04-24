package com.example.demo.jira.items.assigment.infrastructure.persistence.repository;

import com.example.demo.jira.items.assigment.domain.model.Assignment;
import com.example.demo.jira.items.assigment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assigment.infrastructure.persistence.entity.AssignmentEntity;
import com.example.demo.jira.items.assigment.infrastructure.persistence.mapper.AssignmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AssignmentRepositoryImpl implements AssignmentRepository {
    private final AssignmentMapper assignmentMapper;
    private final JpaAssignmentRepository jpa;

    @Override
    public Assignment save(Assignment assignment) {
        AssignmentEntity savedAssignmentEntity = jpa.save(assignmentMapper.toEntity(assignment));
        return assignmentMapper.toDomain(savedAssignmentEntity);
    }

    @Override
    public List<Assignment> findAll() {
        return jpa.findAll()
                .stream().map(assignmentMapper::toDomain)
                .toList();
    }


}
