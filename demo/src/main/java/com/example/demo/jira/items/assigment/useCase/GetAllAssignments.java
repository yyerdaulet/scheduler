package com.example.demo.jira.items.assigment.useCase;

import com.example.demo.jira.items.assigment.domain.model.Assignment;
import com.example.demo.jira.items.assigment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assigment.dto.AssignmentResponse;
import com.example.demo.jira.items.assigment.infrastructure.persistence.mapper.AssignmentMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllAssignments {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;



    public List<AssignmentResponse> execute(){
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments
                .stream()
                .map(
                assignmentMapper::toDto
        ).toList();
    }

}
