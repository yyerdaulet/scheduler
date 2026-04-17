package com.example.demo.jira.items.profile.presentation.controller;

import com.example.demo.jira.items.profile.domain.model.Profile;
import com.example.demo.jira.items.profile.useCases.*;
import com.example.demo.jira.log.LogExecutionTime;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@AllArgsConstructor
public class ProfileController {
    private final CreateProfile createProfile;
    private final UpdateProfile updateProfile;
    private final DeleteProfile deleteProfile;
    private final GetProfiles getAllProfiles;
    private final GetProfile getProfile;


    @GetMapping("/profiles")
    @LogExecutionTime()
    public ResponseEntity<List<Profile>> getAllProfiles(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllProfiles.execute());
    }



    @GetMapping("/profiles/{profileId}")
    @LogExecutionTime()
    public ResponseEntity<Profile> getProfileById(
            @PathVariable("profileId") Long profileId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(getProfile.execute(profileId));
    }

    @PostMapping("/profiles/{profileId}")
    @LogExecutionTime()
    public ResponseEntity<Profile> updateProfile(
            @RequestBody @Valid Profile request,
            @PathVariable Long profileId
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(updateProfile.execute(profileId,request));
    }


    @PostMapping("/profiles")
    @LogExecutionTime()
    public ResponseEntity<Profile> createProfile(
            @RequestBody @Valid Profile request
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(createProfile.execute(request));
    }



    @DeleteMapping("/profiles/{profileId}")
    @LogExecutionTime()
    public ResponseEntity<Void> deleteProfile(
            @PathVariable("profileId") Long profileId
    ){
        deleteProfile.execute(profileId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }








}
