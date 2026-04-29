package com.example.demo.jira.items.assignment.presentation.controller;

import com.example.demo.jira.items.assignment.dto.AssignmentRequest;
import com.example.demo.jira.items.assignment.dto.AssignmentResponse;
import com.example.demo.jira.items.assignment.useCase.CreateAssignment;
import com.example.demo.jira.items.assignment.useCase.GetAllAssignments;
import com.example.demo.jira.items.assignment.useCase.GetAllNonActiveAssignments;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AssignmentController {
    private CreateAssignment createAssignment;
    private GetAllAssignments getAllAssignments;
    private GetAllNonActiveAssignments getAllNonActiveAssignments;

    @PostMapping("/assignments")
    public ResponseEntity<AssignmentResponse> createAssignmentCase(
            @RequestBody AssignmentRequest request
            ){
        return ResponseEntity.status(HttpStatus.OK).body(createAssignment.execute(request));
    }

    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAllAssignmentsCase(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllAssignments.execute());
    }

    @GetMapping("/assignments/nonactive")
    public ResponseEntity<List<AssignmentResponse>> getAllNonActiveAssignmentsCase(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllNonActiveAssignments.execute());
    }

}
