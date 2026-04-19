package com.example.demo.SubjectTest;

import com.example.demo.jira.TaskManagerApplication;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import com.example.demo.jira.items.subject.dto.SubjectRequest;
import com.example.demo.jira.items.subject.dto.SubjectResponse;
import com.example.demo.jira.items.subject.infrastructure.persistence.SubjectMapper;
import com.example.demo.jira.items.subject.useCase.CreateSubject;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TaskManagerApplication.class)
public class CreateSubjectTest {
    private final SubjectRepository repository;

    @Autowired
    public CreateSubjectTest(SubjectRepository repository){
        this.repository = repository;
    }

    @Test
    public void PropertyRequestTest(){
        CreateSubject useCase = new CreateSubject(repository);

        SubjectRequest request = new SubjectRequest(
                "Web",
                5L
        );

        SubjectResponse response = useCase.execute(request);

        assert response.credits().equals(5L);
        assert response.name().equals("Web");
    }




}
