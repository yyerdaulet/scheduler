package com.example.demo.jira.items.group.usecase.get;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;

import java.util.List;

public class GetGroups {
    private final GroupRepository groupRepository;

    public GetGroups(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public List<Group> execute(){
        return groupRepository.findAll();
    }

}
