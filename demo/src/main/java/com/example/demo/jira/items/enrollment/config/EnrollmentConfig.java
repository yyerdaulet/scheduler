package com.example.demo.jira.items.enrollment.config;

import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.EnrollmentMapper;
import com.example.demo.jira.items.enrollment.useCase.EnrollGroupToSubject;
import com.example.demo.jira.items.enrollment.useCase.GetAllEnrollments;
import com.example.demo.jira.items.enrollment.useCase.GetEnrollment;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnrollmentConfig {

    @Bean
    public EnrollGroupToSubject enrollGroupToSubject(EnrollmentRepository enrollmentRepository,
                                                     GroupRepository groupRepository,
                                                     SubjectRepository subjectRepository)
    {
        return new EnrollGroupToSubject(enrollmentRepository,groupRepository,subjectRepository);
    }

    @Bean
    public GetAllEnrollments getAllEnrollments(EnrollmentRepository enrollmentRepository){
        return new GetAllEnrollments(enrollmentRepository);
    }

    @Bean
    public GetEnrollment getEnrollment(EnrollmentRepository enrollmentRepository,
                                       EnrollmentMapper enrollmentMapper){
        return new GetEnrollment(enrollmentRepository,enrollmentMapper);
    }

}
