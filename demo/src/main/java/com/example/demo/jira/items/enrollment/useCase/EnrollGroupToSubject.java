package com.example.demo.jira.items.enrollment.useCase;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnrollGroupToSubject {
    private final EnrollmentRepository enrollmentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    public Enrollment execute(Enrollment enrollment){
        validate(enrollment);
        return enrollmentRepository.save(enrollment);
    }

    private void validate(Enrollment enrollment){
        if(subjectRepository.existById(enrollment.getSubjectId())){
            throw new EntityNotFoundException("Subject not found : " + enrollment.getSubjectId() );
        }
        if(groupRepository.existById(enrollment.getGroupId())){
            throw new EntityNotFoundException("Group not found : " + enrollment.getGroupId());
        }
    }

}
