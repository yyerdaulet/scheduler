package com.example.demo.jira.items.classrooms.infrastructure.persistence.repository;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.ClassRoomEntity;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<ClassRoom> findById(Long id) {
        ClassRoomEntity entity =  jpa.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("ClassRoom Not Found : " + id )
                );

        return Optional.ofNullable(mapper.toDomain(entity));
    }

    @Override
    public Boolean existById(Long classRoomId) {
        return jpa.existsById(classRoomId);
    }

    @Override
    public void delete(Long classRoomId) {
        ClassRoomEntity entity = jpa.findById(classRoomId)
                .orElseThrow(
                        () -> new EntityNotFoundException("ClassRoom Not Found : " + classRoomId )
                );
        jpa.delete(entity);
    }
}
