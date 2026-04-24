package com.example.demo.jira.items.classrooms.config;

import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import com.example.demo.jira.items.classrooms.useCase.CreateClassRoom;
import com.example.demo.jira.items.classrooms.useCase.GetAllClassRooms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassRoomConfig {

    @Bean
    public CreateClassRoom createClassRoom(ClassRoomRepository classRoomRepository,
                                           ClassRoomMapper classRoomMapper){
        return new CreateClassRoom(classRoomRepository,classRoomMapper);
    }

    @Bean
    public GetAllClassRooms getAllClassRooms(ClassRoomMapper classRoomMapper,
                                             ClassRoomRepository classRoomRepository){
        return new GetAllClassRooms(classRoomMapper,
                classRoomRepository);
    }

}
