package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.profile.domain.ProfileRepository;

public class DeleteProfile {
    private final ProfileRepository profileRepository;

    public DeleteProfile(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public void execute(Long profileId){
        profileRepository.delete(profileId);
    }

}
