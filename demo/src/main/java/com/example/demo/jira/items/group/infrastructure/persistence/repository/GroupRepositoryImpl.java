package com.example.demo.jira.items.group.infrastructure.persistence.repository;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.group.infrastructure.persistence.GroupEntity;
import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {
    private final JpaGroupRepository jpa;
    private final GroupMapper mapper;

    @Override
    public List<Group> findAll(){
        return jpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Group> findById(Long id){
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Group save(Group group){
        return mapper.toDomain(
                jpa.save(mapper.toEntity(group))
        );
    }

    @Override
    public List<Group> saveAll(List<Group> groups) {
        List<GroupEntity> entitiesToSave = groups.stream().map(mapper::toEntity).toList();
        List<GroupEntity> savedEntities = jpa.saveAll(entitiesToSave);

        return savedEntities.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Long id){
        jpa.deleteById(id);
    }

    @Override
    public Boolean existById(Long groupId) {
       return jpa.existsById(groupId);
    }

    @Override
    public List<Group> findAllById(List<Long> groupsId) {
        return jpa.findAllById(groupsId).stream().map(
                mapper::toDomain
        ).toList();
    }

}
