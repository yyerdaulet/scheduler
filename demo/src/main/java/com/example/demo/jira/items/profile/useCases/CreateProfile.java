package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.profile.domain.model.Profile;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;

public class CreateProfile {
    private final ProfileRepository profileRepository;

    public CreateProfile(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public Profile execute(Profile profile){
        return profileRepository.save(profile);
    }
}
