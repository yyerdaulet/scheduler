package com.example.demo.jira.items.lessons.useCases;

import com.example.demo.jira.items.lessons.infrastructure.persistence.mapper.LessonMapper;
import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import com.example.demo.jira.items.lessons.domain.repository.LessonRepository;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.lessons.domain.model.Laboratory;
import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.lessons.domain.model.Lecture;
import com.example.demo.jira.items.lessons.domain.model.Seminar;
import com.example.demo.jira.items.group.domain.model.Subgroup;
import com.example.demo.jira.items.lessons.domain.model.Lesson;
import com.example.demo.jira.items.lessons.dto.LessonResponse;
import com.example.demo.jira.items.subject.enums.ActivityType;
import com.example.demo.jira.items.lessons.dto.LessonRequest;
import com.example.demo.jira.items.group.domain.model.Group;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CreateLessons {
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private final LessonMapper lessonMapper;

    public CreateLessons(LessonRepository lessonRepository,
                         EnrollmentRepository enrollmentRepository,
                         SubjectRepository subjectRepository,
                         GroupRepository groupRepository,
                         LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
        this.lessonMapper = lessonMapper;
    }

    public List<LessonResponse> execute(LessonRequest request) {
        Enrollment enrollment = findEnrollmentById(request);
        Subject subject = findSubjectById(enrollment);

        List<Lesson> lessonsToCreate = buildLessons(subject, enrollment.getGroupsId());

        List<Lesson> savedLessons = lessonRepository.saveAll(lessonsToCreate);

        return savedLessons.stream()
                .map(
                lessonMapper::toDto)
                .toList();

    }

    private List<Group> findAllGroupsById(List<Long> groupsId) {
        return groupRepository.findAllById(groupsId);
    }

    private Enrollment findEnrollmentById(LessonRequest request) {
        return enrollmentRepository.findById(request.enrollmentId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Enrollment Not Found : " + request.enrollmentId())
                );
    }

    private Subject findSubjectById(Enrollment enrollment) {
        return subjectRepository.findById(enrollment.getSubjectId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Subject Not found")
                );
    }

    private List<Lesson> buildLessons(Subject subject, List<Long> groupsId) {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(buildLecture(subject,groupsId));

        List<Group> groups = findAllGroupsById(groupsId);
        List<Lesson> restLessons = resolveLessons(subject,groups);
        lessons.addAll(restLessons);
        return lessons;
    }

    private List<Lesson> resolveLessons(Subject subject, List<Group> groups) {
        System.out.println(subject.getType());
        if (subject.getType() == ActivityType.LABORATORY) {
            return buildLaboratories(groups, subject);
        }else {
            return buildSeminars(groups, subject);
        }
    }

    private List<Lesson> buildSeminars(List<Group> groups, Subject subject) {
        List<Lesson> seminars = new ArrayList<>();
        for (Group group : groups) {
            Lesson seminar = buildSeminar(subject, group);
            seminars.add(seminar);
        }
        return seminars;
    }

    private Seminar buildSeminar(Subject subject, Group group) {
        return new Seminar(
                null,
                subject.getName(),
                resolveHours(subject.getHours()),
                subject.getType(),
                group.getId()
        );
    }

    private List<Lesson> buildLaboratories(List<Group> groups, Subject subject) {
        List<Lesson> laboratories = new ArrayList<>();
        for (Group group : groups) {
            System.out.println(group.getName());
            System.out.println(group.getSubgroups());
            for (Subgroup subgroup : group.getSubgroups()) {
                Lesson laboratory = buildLaboratory(subject, group, subgroup);
                System.out.println(subgroup.getName());
                laboratories.add(laboratory);
            }
        }

        return laboratories;
    }

    private Laboratory buildLaboratory(Subject subject, Group group, Subgroup subgroup) {
        return new Laboratory(
                null,
                subject.getName(),
                resolveHours(subject.getHours()),
                subject.getType(),
                group.getId(),
                subgroup.getId()
        );
    }


    private Lecture buildLecture(Subject subject,List<Long> groupsId) {
        return new Lecture(
                null,
                subject.getName(), 15L,
                ActivityType.LECTURE,
                groupsId
            );
    }

    private Long resolveHours(Long hours){

        if(hours.equals(45L)){
            return 30L;
        }


        return 15L;
    }


}
