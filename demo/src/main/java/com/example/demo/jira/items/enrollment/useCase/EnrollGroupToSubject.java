package com.example.demo.jira.items.enrollment.useCase;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.enrollment.dto.EnrollmentRequest;
import com.example.demo.jira.items.enrollment.dto.EnrollmentResponse;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnrollGroupToSubject {
    private final EnrollmentRepository enrollmentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    public EnrollmentResponse execute(EnrollmentRequest request){
        validate(request);
        Enrollment enrollment = new Enrollment(
                null,
                request.subjectId(),
                request.groupsId()
        );

        Enrollment savedEnrollment =  enrollmentRepository.save(enrollment);

        return new EnrollmentResponse(
                savedEnrollment.getId(),
                savedEnrollment.getSubjectId(),
                savedEnrollment.getGroupsId()
        );
    }

    private void validate(EnrollmentRequest enrollment){
        if(!subjectRepository.existById(enrollment.subjectId())){
            throw new EntityNotFoundException("Subject not found : " + enrollment.subjectId() );
        }
    }

}
