package com.example.demo.jira.items.group.usecase.get;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.group.dto.GroupResponse;

import java.util.List;

public class GetGroups {
    private final GroupRepository groupRepository;

    public GetGroups(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public List<GroupResponse> execute(){
        List<Group> groups =  groupRepository.findAll();
        return groups.stream().map(
                (model) -> new GroupResponse(
                        model.getId(),
                        model.getName(),
                        model.getEnrolledYear(),
                        model.getDirection(),
                        model.getSubgroups()
        )).toList();
    }

}
