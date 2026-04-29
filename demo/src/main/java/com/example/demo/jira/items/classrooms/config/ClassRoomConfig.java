package com.example.demo.jira.items.classrooms.config;

import com.example.demo.jira.items.assignment.domain.repository.AssignmentRepository;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.domain.repository.TimeSlotRepository;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import com.example.demo.jira.items.classrooms.useCase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassRoomConfig {

    @Bean
    public CreateClassRoom createClassRoom(ClassRoomRepository classRoomRepository,
                                           ClassRoomMapper classRoomMapper,
                                           TimeSlotRepository timeSlotRepository){
        return new CreateClassRoom(classRoomRepository,classRoomMapper,timeSlotRepository);
    }

    @Bean
    public GetAllClassRooms getAllClassRooms(ClassRoomMapper classRoomMapper,
                                             ClassRoomRepository classRoomRepository){
        return new GetAllClassRooms(classRoomMapper,
                classRoomRepository);
    }

    @Bean
    public BookClassRoom bookClassRoom(ClassRoomRepository classRoomRepository,
                                       ClassRoomMapper classRoomMapper,
                                       AssignmentRepository assignmentRepository,
                                       TimeSlotRepository timeSlotRepository){
        return new BookClassRoom(classRoomRepository,
                classRoomMapper,assignmentRepository,
                timeSlotRepository);
    }

    @Bean
    public DeleteClassRoom deleteClassRoom(ClassRoomRepository repository){
        return new DeleteClassRoom(repository);
    }

    @Bean
    public UpdateClassRoom updateClassRoom(ClassRoomRepository repository,
                                           ClassRoomMapper mapper){
        return new UpdateClassRoom(repository,mapper);
    }

}
