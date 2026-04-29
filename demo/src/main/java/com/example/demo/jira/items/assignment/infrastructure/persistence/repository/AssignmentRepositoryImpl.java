package com.example.demo.jira.items.assignment.infrastructure.persistence.repository;

import com.example.demo.jira.items.assignment.domain.model.Assignment;
import com.example.demo.jira.items.assignment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assignment.infrastructure.persistence.entity.AssignmentEntity;
import com.example.demo.jira.items.assignment.infrastructure.persistence.mapper.AssignmentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Assignment> findById(Long id) {
        return Optional.ofNullable(
                assignmentMapper.toDomain(jpa.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Assignment Not Found")
                        )
                )
        );
    }

    @Override
    public boolean existByLessonId(Long lessonId) {
        return jpa.existsByLessonId(lessonId);
    }

    @Override
    public List<Assignment> findAllNonActive() {
        return jpa.findAllByIsActiveFalse()
                .stream().map(assignmentMapper::toDomain)
                .toList();
    }


}
