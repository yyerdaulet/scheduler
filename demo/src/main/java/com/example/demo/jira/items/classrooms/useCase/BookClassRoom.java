package com.example.demo.jira.items.classrooms.useCase;

import com.example.demo.jira.items.classrooms.enums.WeekDay;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.assignment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.classrooms.domain.repository.TimeSlotRepository;
import com.example.demo.jira.items.classrooms.dto.BookClassRoomRequest;
import com.example.demo.jira.items.assignment.domain.model.Assignment;
import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.model.Timeslot;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.enums.RoomState;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

public class BookClassRoom {
    private ClassRoomRepository classRoomRepository;
    private ClassRoomMapper classRoomMapper;
    private AssignmentRepository assignmentRepository;
    private TimeSlotRepository timeSlotRepository;

    public ClassRoomResponse execute(BookClassRoomRequest request) {
        ClassRoom classRoom = findClassRoomById(request);
        Assignment assignment = findByAssignmentId(request.assignmentId());

        validateAssignment(assignment);

        List<Timeslot> timeslots = classRoom.getTimeslots();
        LocalTime startTime = request.lessonStartTime();

        List<Timeslot> toSave = new ArrayList<>();
        toSave.add(bookTimeSlot(request,request.day(),startTime,timeslots));

        if(assignment.getDuration().equals(2L)) {
            toSave.add(bookTimeSlot(request,request.day(),startTime.plusHours(1),timeslots));
        }

        toSave.forEach(timeSlotRepository::save);
        assignment.setIsActive(true);
        assignmentRepository.save(assignment);

        classRoom.setTimeslots(timeslots);

        return classRoomMapper.toDto(findClassRoomById(request));
    }

    private Timeslot bookTimeSlot(BookClassRoomRequest request, WeekDay day, LocalTime startTime, List<Timeslot> timeslots) {
        Timeslot timeslot = findTimeSlot(day, startTime, timeslots);
        validateTimeSlot(timeslot);

        timeslot.setRoomState(RoomState.BOOKED);
        timeslot.setBookedByAssignmentId(request.assignmentId());
        return timeslot;
    }

    private void validateAssignment(Assignment assignment) {
        if(assignment.getIsActive() != null && assignment.getIsActive()){
            throw new IllegalArgumentException("Assignment is active already");
        }
    }

    private Assignment findByAssignmentId(Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Assignment Not Found :" + assignmentId)
                );
    }

    private  Timeslot findTimeSlot(WeekDay day,LocalTime startTime, List<Timeslot> timeslots) {
        return timeslots.stream().filter(
                        (t) ->
                                t.getWeekday().equals(day) &&
                                        t.getStart().equals(startTime)
                ).findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Time Slot Not Found")
                );
    }

    private  void validateTimeSlot(Timeslot timeSlotToBook) {
        RoomState currentState = timeSlotToBook.getRoomState();
        if(currentState.equals(RoomState.BOOKED) || currentState.equals(RoomState.NOT_AVAILABLE)){
            throw new IllegalArgumentException("The timeslot is already booked or not available for now");
        }
    }

    private ClassRoom findClassRoomById(BookClassRoomRequest request) {
        return classRoomRepository.findById(request.classRoomId())
                .orElseThrow(
                        () -> new EntityNotFoundException("ClassRoom Not Found")
                );
    }
}
