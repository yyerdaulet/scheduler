package com.example.demo.jira.items.subject.config;

import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import com.example.demo.jira.items.subject.useCase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SubjectConfiguration {
    @Bean
    public GetSubjects getSubjects(SubjectRepository repository){
        return new GetSubjects(repository);
    }

    @Bean
    public GetSubject getSubject(SubjectRepository repository){
        return new GetSubject(repository);
    }

    @Bean
    public CreateSubject createSubject(SubjectRepository repository){
        return new CreateSubject(repository);
    }

    @Bean
    public UpdateSubject updateSubject(SubjectRepository repository){
        return new UpdateSubject(repository);
    }

    @Bean
    public DeleteSubject deleteSubject(SubjectRepository repository){
        return new DeleteSubject(repository);
    }
}
