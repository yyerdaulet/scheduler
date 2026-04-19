package com.example.demo.jira.items.group.domain.repository;

import com.example.demo.jira.items.group.domain.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> findAll();
    Optional<Group> findById(Long id);
    Group save(Group group);
    void delete(Long id);
    Boolean existById(Long groupId);
}
