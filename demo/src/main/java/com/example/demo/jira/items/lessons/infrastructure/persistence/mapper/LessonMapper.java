package com.example.demo.jira.items.lessons.infrastructure.persistence.mapper;

import com.example.demo.jira.items.lessons.domain.model.Laboratory;
import com.example.demo.jira.items.lessons.domain.model.Lecture;
import com.example.demo.jira.items.lessons.domain.model.Lesson;
import com.example.demo.jira.items.lessons.domain.model.Seminar;
import com.example.demo.jira.items.lessons.dto.LessonResponse;
import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.LaboratoryEntity;
import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.LectureEntity;
import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.LessonEntity;
import com.example.demo.jira.items.lessons.infrastructure.persistence.entity.SeminarEntity;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {
    public LessonEntity toEntity(Lesson lesson) {
        return switch (lesson.getType()){
            case LECTURE -> toLectureEntity( (Lecture) lesson);
            case SEMINAR -> toSeminarEntity( (Seminar) lesson);
            case LABORATORY -> toLaboratoryEntity( (Laboratory) lesson);
        };
    }

    private LessonEntity toLaboratoryEntity(Laboratory laboratory) {
        return new LaboratoryEntity(
          null, laboratory.getSubjectName(),
          laboratory.getHours(),
          laboratory.getGroupId(),
          laboratory.getSubgroupId()
        );
    }

    public LessonEntity toSeminarEntity(Seminar seminar) {
        return new SeminarEntity(
                null,
                seminar.getSubjectName(),
                seminar.getHours(),
                seminar.getGroupId()
        );
    }

    public LessonEntity toLectureEntity(Lecture lecture) {
        return new LectureEntity(
                null,
                lecture.getSubjectName(),
                lecture.getHours(),
                lecture.getGroupsId()
        );
    }


    public Lesson toDomain(LessonEntity lessonEntity) {
        return switch (lessonEntity.getType()){
            case LECTURE -> toLectureDomain( (LectureEntity) lessonEntity);
            case SEMINAR -> toSeminarDomain( (SeminarEntity) lessonEntity);
            case LABORATORY -> toLaboratoryDomain( (LaboratoryEntity) lessonEntity);
        };
    }

    private Lesson toLaboratoryDomain(LaboratoryEntity lessonEntity) {
        return new Laboratory(
                lessonEntity.getId(),
                lessonEntity.getSubjectName(),
                lessonEntity.getHours(),
                lessonEntity.getType(),
                lessonEntity.getGroupId(),
                lessonEntity.getSubgroupId()
        );
    }

    private Lesson toSeminarDomain(SeminarEntity lessonEntity) {
        return new Seminar(
                lessonEntity.getId(),
                lessonEntity.getSubjectName(),
                lessonEntity.getHours(),
                lessonEntity.getType(),
                lessonEntity.getGroupId()
        );
    }

    private Lesson toLectureDomain(LectureEntity lessonEntity) {
        return new Lecture(
           lessonEntity.getId(),
                lessonEntity.getSubjectName(),
                lessonEntity.getHours(),
                lessonEntity.getType(),
                lessonEntity.getGroupsId()
        );
    }

    public LessonResponse toDto(Lesson lesson) {
        return switch (lesson.getType()){
            case LECTURE -> toLectureDto((Lecture)lesson);
            case SEMINAR -> toSeminarDto( (Seminar) lesson);
            case LABORATORY -> toLaboratoryDto( (Laboratory) lesson);
        };
    }

    private LessonResponse toLaboratoryDto(Laboratory lesson) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getSubjectName(),
                lesson.getHours(),
                lesson.getType(),
                null,
                lesson.getGroupId(),
                lesson.getSubgroupId()
        );
    }

    private LessonResponse toSeminarDto(Seminar lesson) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getSubjectName(),
                lesson.getHours(),
                lesson.getType(),
                null,
                lesson.getGroupId(),
                null
        );
    }

    private LessonResponse toLectureDto(Lecture lesson) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getSubjectName(),
                lesson.getHours(),
                lesson.getType(),
                lesson.getGroupsId(),
                null,
                null
        );
    }
}
