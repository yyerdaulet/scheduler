package com.example.demo.jira.items.assigment.config;

import com.example.demo.jira.items.assigment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assigment.infrastructure.persistence.mapper.AssignmentMapper;
import com.example.demo.jira.items.assigment.useCase.CreateAssignment;
import com.example.demo.jira.items.assigment.useCase.GetAllAssignments;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssignmentConfiguration {

    @Bean
    public CreateAssignment createAssignment(AssignmentRepository assignmentRepository,
                                             AssignmentMapper assignmentMapper,
                                             LessonRepository lessonRepository,
                                             ProfileRepository profileRepository){
        return new CreateAssignment(assignmentRepository,
                assignmentMapper,
                lessonRepository,profileRepository);
    }

    @Bean
    public GetAllAssignments getAllAssignments(
            AssignmentRepository assignmentRepository,
            AssignmentMapper assignmentMapper
    ){
        return new GetAllAssignments(
                assignmentRepository,
                assignmentMapper
        );
    }
}
