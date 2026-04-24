package com.example.demo.jira.items.assigment.useCase;

import com.example.demo.jira.items.assigment.domain.model.Assignment;
import com.example.demo.jira.items.assigment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assigment.dto.AssignmentRequest;
import com.example.demo.jira.items.assigment.dto.AssignmentResponse;
import com.example.demo.jira.items.assigment.infrastructure.persistence.mapper.AssignmentMapper;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateAssignment {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final LessonRepository lessonRepository;
    private final ProfileRepository profileRepository;


    public AssignmentResponse execute(AssignmentRequest request) {
        validateRequest(request);

        Assignment assignment = buildAssignment(request);
;
        Assignment savedAssignment = assignmentRepository.save(assignment);

        return assignmentMapper.toDto(savedAssignment);
    }

    private  Assignment buildAssignment(AssignmentRequest request) {
        return new Assignment(
                null,
                request.profileId(),
                request.lessonId()
                );
    }

    private void validateRequest(AssignmentRequest request) {
        validateProfile(request.profileId());
        validateLesson(request.lessonId());
    }

    private void validateLesson(Long lessonId) {
        if(!lessonRepository.existById(lessonId)){
            throw new EntityNotFoundException("Lesson not found : " + lessonId);
        }
    }

    private void validateProfile(Long profileId) {
        if(!profileRepository.existById(profileId)){
            throw new EntityNotFoundException("Profile Not Found : " + profileId);
        }
    }
}
