package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.profile.domain.model.Profile;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;

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
