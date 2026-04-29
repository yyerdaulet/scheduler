package com.example.demo.jira.items.classrooms.infrastructure.persistence.repository;

import com.example.demo.jira.items.classrooms.domain.model.Timeslot;
import com.example.demo.jira.items.classrooms.domain.repository.TimeSlotRepository;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.TimeSlotEntity;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class TimeslotRepositoryImpl implements TimeSlotRepository {
    private final JpaTimeslotRepository jpa;
    private final ClassRoomMapper mapper;

    @Override
    public Timeslot save(Timeslot timeslot) {
        TimeSlotEntity entity = jpa.findById(timeslot.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Time slot Not Found ")
                );

        entity.setRoomState(timeslot.getRoomState());
        entity.setBookedByAssignmentId(timeslot.getBookedByAssignmentId());

        return mapper.toTimeSlotDomain(jpa.save(entity));
    }

    @Override
    public List<Timeslot> saveAll(List<Timeslot> timeslots) {
        List<TimeSlotEntity> entities = timeslots
                .stream()
                .map(mapper::toTimeSlotEntity)
                .toList();

        return entities.stream()
                .map(mapper::toTimeSlotDomain)
                .toList();
    }
}
