package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.profile.domain.Profile;
import com.example.demo.jira.items.profile.domain.ProfileRepository;
import com.example.demo.jira.items.profile.repo.JpaProfileRepository;

public class CreateProfile {
    private final ProfileRepository profileRepository;

    public CreateProfile(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public Profile execute(Profile profile){
        return profileRepository.save(profile);
    }
}
