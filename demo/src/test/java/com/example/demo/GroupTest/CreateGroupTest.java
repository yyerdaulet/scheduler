package com.example.demo.GroupTest;

import com.example.demo.jira.TaskManagerApplication;
import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.group.dto.GroupRequest;
import com.example.demo.jira.items.group.dto.GroupResponse;
import com.example.demo.jira.items.group.enums.Direction;
import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import com.example.demo.jira.items.group.usecase.create.CreateGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TaskManagerApplication.class)
public class CreateGroupTest {
    private final GroupMapper mapper;
    private final GroupRepository groupRepository;

    @Autowired
    public CreateGroupTest(
            GroupMapper groupMapper,
            GroupRepository groupRepository
    ){
        this.groupRepository = groupRepository;
        this.mapper = groupMapper;
    }

    @Test
    public void properDataTest(){
        CreateGroup createGroup = new CreateGroup(groupRepository,mapper);
        GroupRequest request = new GroupRequest(
                "IS23-02",
                2023L,
                Direction.PROGRAMMING
        );

        GroupResponse response = createGroup.execute(request);

        assert (response.enrolledYear() == 2023L);
        assert (response.name().equals("IS23-02"));
        assert (response.direction() == Direction.PROGRAMMING);
    }


}
