package com.example.demo.jira.items.assignment.useCase;

import com.example.demo.jira.items.assignment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assignment.dto.AssignmentResponse;
import com.example.demo.jira.items.assignment.infrastructure.persistence.mapper.AssignmentMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllNonActiveAssignments {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    public List<AssignmentResponse> execute(){
        return assignmentRepository.findAllNonActive()
                .stream()
                .map(assignmentMapper::toDto)
                .toList();
    }

}
