package com.example.demo.jira.items.group.presentation.controller;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.usecase.create.CreateGroup;
import com.example.demo.jira.items.group.usecase.delete.DeleteGroup;
import com.example.demo.jira.items.group.usecase.get.GetGroup;
import com.example.demo.jira.items.group.usecase.get.GetGroups;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GetGroups getGroups;
    private final GetGroup getGroup;
    private final CreateGroup createGroup;
    private final DeleteGroup deleteGroup;


    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups(){
        return ResponseEntity.status(HttpStatus.OK).body(getGroups.execute());
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<Group> getGroup(
            @PathVariable Long groupId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(getGroup.execute(groupId));
    }

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(
            @RequestBody Group request
            ){
        return ResponseEntity.status(HttpStatus.OK).body(createGroup.execute(request));
    }

    @PutMapping("/groups/{groupId}")
    public ResponseEntity<Group> updateGroup(
            @RequestBody Group request,
            @PathVariable Long groupId
            ){
        return null;
    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<Void> deleteGroup(
        @PathVariable Long groupId
    ){
        deleteGroup.execute(groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
