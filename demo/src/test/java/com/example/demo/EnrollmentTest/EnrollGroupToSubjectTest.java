package com.example.demo.EnrollmentTest;

import com.example.demo.jira.TaskManagerApplication;
import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.enrollment.dto.EnrollmentRequest;
import com.example.demo.jira.items.enrollment.dto.EnrollmentResponse;
import com.example.demo.jira.items.enrollment.useCase.EnrollGroupToSubject;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TaskManagerApplication.class)
public class EnrollGroupToSubjectTest {
    private final EnrollmentRepository enrollmentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public EnrollGroupToSubjectTest(EnrollmentRepository enrollmentRepository,
                                    GroupRepository groupRepository,
                                    SubjectRepository subjectRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
    }

    @Test
    public void propertyDataTest(){
        EnrollGroupToSubject useCase = new EnrollGroupToSubject(
                enrollmentRepository,
                groupRepository,
                subjectRepository);

        EnrollmentRequest request = new EnrollmentRequest(
                1L,
                1L
        );

        EnrollmentResponse response = useCase.execute(request);

        assert response.subjectId().equals(1L);
        assert response.groupId().equals(1L);

    }
}
