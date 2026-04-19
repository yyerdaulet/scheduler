package com.example.demo.jira.items.group.usecase.create;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.group.dto.GroupRequest;
import com.example.demo.jira.items.group.dto.GroupResponse;
import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CreateGroup {
    private final GroupRepository groupRepository;
    private final GroupMapper mapper;


    public GroupResponse execute(GroupRequest request){
        Group group = new Group(
                null,
                request.name(),
                request.enrolledYear(),
                request.direction()
        );

        Group savedGroup = groupRepository.save(group);
        return mapper.toDto(savedGroup);
    }

}
