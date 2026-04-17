package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.profile.domain.Profile;
import com.example.demo.jira.items.profile.domain.ProfileRepository;

import java.util.List;

public class GetProfiles {
    private final ProfileRepository profileRepository;

    public GetProfiles(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public List<Profile> execute(){
        return profileRepository.findAll();
    }
}
