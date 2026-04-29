package com.example.demo.jira.items.classrooms.infrastructure.persistence.entity;

import com.example.demo.jira.items.classrooms.enums.RoomState;
import com.example.demo.jira.items.classrooms.enums.WeekDay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class TimeSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start")
    private LocalTime start;

    @Column(name="finish")
    private LocalTime finish;

    @Column(name="roomState")
    @Enumerated(EnumType.STRING)
    private RoomState roomState;

    @Column(name = "weekday")
    @Enumerated(EnumType.STRING)
    private WeekDay weekday;

    @Column(name = "bookedAssignmentId")
    private Long bookedByAssignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id")
    private ClassRoomEntity classRoom;
}
