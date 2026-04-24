package com.example.demo.jira.items.assigment.presentation.controller;

import com.example.demo.jira.items.assigment.dto.AssignmentRequest;
import com.example.demo.jira.items.assigment.dto.AssignmentResponse;
import com.example.demo.jira.items.assigment.useCase.CreateAssignment;
import com.example.demo.jira.items.assigment.useCase.GetAllAssignments;
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

}
