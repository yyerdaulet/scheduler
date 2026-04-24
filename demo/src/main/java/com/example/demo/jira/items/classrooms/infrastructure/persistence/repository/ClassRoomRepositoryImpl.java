package com.example.demo.jira.items.classrooms.infrastructure.persistence.repository;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.ClassRoomEntity;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ClassRoomRepositoryImpl implements ClassRoomRepository {
    private final JpaClassRoomRepository jpa;
    private final ClassRoomMapper mapper;

    @Override
    public ClassRoom save(ClassRoom classRoom) {
        ClassRoomEntity saved = jpa.save(mapper.toEntity(classRoom));
        return mapper.toDomain(saved);
    }

    @Override
    public List<ClassRoom> findAll() {
        List<ClassRoomEntity> entities = jpa.findAll();
        return entities.stream()
                .map(mapper::toDomain)
                .toList();
    }
}
