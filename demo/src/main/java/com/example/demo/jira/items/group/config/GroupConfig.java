package com.example.demo.jira.items.group.config;

import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import com.example.demo.jira.items.group.usecase.create.CreateGroup;
import com.example.demo.jira.items.group.usecase.delete.DeleteGroup;
import com.example.demo.jira.items.group.usecase.get.GetGroup;
import com.example.demo.jira.items.group.usecase.get.GetGroups;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupConfig {

    @Bean
    public GetGroups getGroups(GroupRepository repository){
        return new GetGroups(repository);
    }

    @Bean
    public CreateGroup createGroup(GroupRepository repository, GroupMapper mapper){
        return new CreateGroup(repository,mapper);
    }

    @Bean
    public GetGroup getGroup(GroupRepository repository){
        return new GetGroup(repository);
    }

    @Bean
    public DeleteGroup deleteGroup(GroupRepository repository){
        return new DeleteGroup(repository);
    }
}
