package com.example.demo.jira.items.enrollment.presentation.controller;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.useCase.EnrollGroupToSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class controller {
    private final EnrollGroupToSubject enrollGroupToSubject;

    @PostMapping("/enrollment")
    public ResponseEntity<Enrollment> enroll(
            @RequestBody Enrollment request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(enrollGroupToSubject.execute(request));
    }

}
