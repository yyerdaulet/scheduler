package com.example.demo.jira.items.lessons.config;

import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.lessons.infrastructure.persistence.mapper.LessonMapper;
import com.example.demo.jira.items.lessons.useCases.CreateLessons;
import com.example.demo.jira.items.lessons.useCases.GetAllLessons;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LessonConfig {

    @Bean
    public CreateLessons createLessons(LessonRepository lessonRepository,
                                       EnrollmentRepository enrollmentRepository,
                                       SubjectRepository subjectRepository,
                                       GroupRepository groupRepository,
                                       LessonMapper lessonMapper){
        return new CreateLessons(lessonRepository,enrollmentRepository,
                subjectRepository,groupRepository,
                lessonMapper);
    }

    @Bean
    public GetAllLessons getAllLessons(LessonRepository lessonRepository,LessonMapper lessonMapper){
        return new GetAllLessons(lessonRepository,lessonMapper);
    }


}
