package com.example.demo.jira.items.group.usecase.create;

import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.group.domain.model.Subgroup;
import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.dto.GroupResponse;
import com.example.demo.jira.items.group.dto.GroupRequest;
import com.example.demo.jira.items.subject.domain.model.Subject;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class CreateGroup {
    private final GroupRepository groupRepository;
    private final GroupMapper mapper;

    public GroupResponse execute(GroupRequest request){
        Group group = buildGroup(request);

        Group savedGroup = groupRepository.save(group);


        return mapper.toDto(savedGroup);
    }

    private  Group buildGroup(GroupRequest request) {
        List<Subgroup> subgroups = new ArrayList<>();

        if(isOverFilled(request)){
            subgroups.addAll(createSubgroups(request));
        }

        return new Group(
                null,
                request.name(),
                request.enrolledYear(),
                request.direction(),
                subgroups
        );
    }

    private void addSubgroups(GroupRequest request, Group group) {
        List<Subgroup> subgroups = createSubgroups(request);
        group.addSubgroups(subgroups);
    }

    private List<Subgroup> createSubgroups(GroupRequest request) {
        Subgroup subgroupA = buildSubgroup(1L,  request.name()+"A");
        Subgroup subgroupB = buildSubgroup(2L,  request.name()+"B");
        return List.of(subgroupA,subgroupB);
    }

    private Subgroup buildSubgroup(Long id,String name) {
        return new Subgroup(
                id,
                name
        );
    }

    private boolean isOverFilled(GroupRequest request) {
        return request.size() > 10;
    }
}
