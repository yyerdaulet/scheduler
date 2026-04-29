package com.example.demo.jira.items.classrooms.useCase;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.model.Timeslot;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.domain.repository.TimeSlotRepository;
import com.example.demo.jira.items.classrooms.dto.ClassRoomRequest;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.enums.RoomState;
import com.example.demo.jira.items.classrooms.enums.WeekDay;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CreateClassRoom {
    private final ClassRoomRepository classRoomRepository;
    private final ClassRoomMapper classRoomMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final static int DAY_START = 8;
    private final static int DAY_FINISH = 19;


    public ClassRoomResponse execute(ClassRoomRequest request) {
        ClassRoom classRoom = buildClassRoom(request);

        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);

        return classRoomMapper.toDto(savedClassRoom);
    }

    private ClassRoom buildClassRoom(ClassRoomRequest request) {
        List<Timeslot> timeSlots = timeSlotRepository.saveAll(createTimeslots());

        return new ClassRoom(
                null,
                request.number(),
                request.size(),
                timeSlots
        );
    }

    private List<Timeslot> createTimeslots() {
        List<Timeslot> timeslots = new ArrayList<>();

        for (WeekDay day : WeekDay.values()) {
            for (int currentTime = DAY_START; currentTime < DAY_FINISH; currentTime++) {

                LocalTime start = LocalTime.of(currentTime, 0);
                LocalTime end = LocalTime.of(currentTime, 50);

                Timeslot timeslot = buildTimeSlot(day, start, end);

                timeslots.add(timeslot);
            }
        }

        return timeslots;
    }

    private Timeslot buildTimeSlot(WeekDay day, LocalTime start, LocalTime end) {
        return new Timeslot(
                null,
                start,
                end,
                RoomState.AVAILABLE,
                day,
                null
        );
    }
}
