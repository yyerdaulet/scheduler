package com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.model.Timeslot;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.ClassRoomEntity;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.TimeSlotEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassRoomMapper {
    public ClassRoomEntity toEntity(ClassRoom classRoom) {
        ClassRoomEntity entity = new ClassRoomEntity();

        entity.setId(classRoom.getId());
        entity.setNumber(classRoom.getNumber());
        entity.setSize(classRoom.getSize());


        List<TimeSlotEntity> timeSlotEntities = classRoom.getTimeslots()
                .stream()
                .map(t -> toTimeSlotEntity(t,entity))
                .toList();

        entity.setTimeslot(timeSlotEntities);

        return entity;
    }

    public ClassRoom toDomain(ClassRoomEntity saved) {
        List<Timeslot> timeslots = saved.getTimeslot()
                .stream()
                .map(this::toTimeSlotDomain)
                .toList();

        return new ClassRoom(
                saved.getId(),
                saved.getNumber(),
                saved.getSize(),
                timeslots
        );
    }

    public Timeslot toTimeSlotDomain(TimeSlotEntity entity){
        return new Timeslot(
                entity.getId(),
                entity.getStart(),
                entity.getFinish(),
                entity.getRoomState(),
                entity.getWeekday(),
                entity.getBookedByAssignmentId()
        );
    }

    public ClassRoomResponse toDto(ClassRoom savedClassRoom) {
        return new ClassRoomResponse(
                savedClassRoom.getId(),
                savedClassRoom.getNumber(),
                savedClassRoom.getSize(),
                savedClassRoom.getTimeslots()
        );
    }

    public TimeSlotEntity toTimeSlotEntity(Timeslot timeslot,ClassRoomEntity classRoomEntity ){
        return new TimeSlotEntity(
                timeslot.getId(),
                timeslot.getStart(),
                timeslot.getFinish(),
                timeslot.getRoomState(),
                timeslot.getWeekday(),
                timeslot.getBookedByAssignmentId(),
                classRoomEntity
        );
    }

    public TimeSlotEntity toTimeSlotEntity(Timeslot timeslot) {
        TimeSlotEntity entity = new TimeSlotEntity();
        entity.setId(timeslot.getId());
        entity.setStart(timeslot.getStart());
        entity.setFinish(timeslot.getFinish());
        entity.setRoomState(timeslot.getRoomState());
        entity.setWeekday(timeslot.getWeekday());
        entity.setBookedByAssignmentId(timeslot.getBookedByAssignmentId());

        return entity;
    }
}
