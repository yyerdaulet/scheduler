package com.example.demo.jira.items.group.usecase.create;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;

public class CreateGroup {
    private final GroupRepository groupRepository;

    public CreateGroup(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public Group execute(Group group){
        return groupRepository.save(group);
    }

}
