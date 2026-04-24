package com.example.demo.jira.items.enrollment.presentation.controller;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.dto.EnrollmentRequest;
import com.example.demo.jira.items.enrollment.dto.EnrollmentResponse;
import com.example.demo.jira.items.enrollment.useCase.EnrollGroupToSubject;
import com.example.demo.jira.items.enrollment.useCase.GetAllEnrollments;
import com.example.demo.jira.items.enrollment.useCase.GetEnrollment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class controller {
    private final EnrollGroupToSubject enrollGroupToSubject;
    private final GetAllEnrollments getAllEnrollments;
    private final GetEnrollment getEnrollment;


    @PostMapping("/enrollments")
    public ResponseEntity<EnrollmentResponse> enroll(
            @RequestBody EnrollmentRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(enrollGroupToSubject.execute(request));
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollments(
    ){
        return ResponseEntity.status(HttpStatus.OK).body(getAllEnrollments.execute());
    }

    @GetMapping("/enrollments/{enrollmentId}")
    public ResponseEntity<EnrollmentResponse> getEnrollment(
        @PathVariable("enrollmentId") Long enrollmentId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(getEnrollment.execute(enrollmentId));
    }


}
