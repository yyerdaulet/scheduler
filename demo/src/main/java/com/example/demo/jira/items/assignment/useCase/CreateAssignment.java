package com.example.demo.jira.items.assignment.useCase;

import com.example.demo.jira.items.assignment.domain.model.Assignment;
import com.example.demo.jira.items.assignment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.assignment.dto.AssignmentRequest;
import com.example.demo.jira.items.assignment.dto.AssignmentResponse;
import com.example.demo.jira.items.assignment.infrastructure.persistence.mapper.AssignmentMapper;
import com.example.demo.jira.items.lessons.domain.model.Lesson;
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

        Long duration = resolveDuration(request);
        return new Assignment(
                null,
                request.profileId(),
                request.lessonId(),
                false,
                duration
                );
    }

    private Long resolveDuration(AssignmentRequest request) {
        Lesson lesson = findLessonById(request);
        Long hours = lesson.getHours();
        if(hours.equals(30L)){
            System.out.println("Case 2");
            return 2L;
        }
        return 1L;
    }

    private Lesson findLessonById(AssignmentRequest request) {
        return lessonRepository.findById(request.lessonId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Lesson Not Found")
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
        if(assignmentRepository.existByLessonId(lessonId)){
            throw new IllegalArgumentException("Lesson already has an assignment");
        }
    }

    private void validateProfile(Long profileId) {
        if(!profileRepository.existById(profileId)){
            throw new EntityNotFoundException("Profile Not Found : " + profileId);
        }
    }
}
