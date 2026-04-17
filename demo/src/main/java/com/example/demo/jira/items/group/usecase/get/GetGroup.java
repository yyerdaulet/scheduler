package com.example.demo.jira.items.group.usecase.get;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;

public class GetGroup {
    private final GroupRepository groupRepository;

    public GetGroup(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public Group execute(Long groupId){
        return groupRepository.findById(groupId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Group not found")
                );
    }
}
