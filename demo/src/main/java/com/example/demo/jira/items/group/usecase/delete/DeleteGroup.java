package com.example.demo.jira.items.group.usecase.delete;

import com.example.demo.jira.items.group.domain.repository.GroupRepository;

public class DeleteGroup {
    private final GroupRepository groupRepository;

    public DeleteGroup(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public void execute(Long groupId){
        groupRepository.delete(groupId);
    }
}
