package com.example.demo.jira.items.enrollment.config;

import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.enrollment.useCase.EnrollGroupToSubject;
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
}
