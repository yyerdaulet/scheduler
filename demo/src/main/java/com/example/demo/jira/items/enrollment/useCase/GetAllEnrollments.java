package com.example.demo.jira.items.enrollment.useCase;


import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.enrollment.dto.EnrollmentResponse;

import java.util.List;

public class GetAllEnrollments {
    private final EnrollmentRepository enrollmentRepository;

    public GetAllEnrollments(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<EnrollmentResponse> execute(){
        List<Enrollment> enrollments = enrollmentRepository.findAll();

        return enrollments.stream().map(
                (model) ->
                        new EnrollmentResponse(
                                model.getId(),
                                model.getSubjectId(),
                                model.getGroupsId()
                        )
        ).toList();
    }

}
