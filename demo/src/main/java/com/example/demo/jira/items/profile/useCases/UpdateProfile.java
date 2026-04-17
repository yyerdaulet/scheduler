package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.profile.domain.model.Profile;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;

public class UpdateProfile {
    private final ProfileRepository profileRepository;

    public UpdateProfile(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public Profile execute(Long profileId, Profile request){
        validate(profileId);
        return profileRepository.save(request);  // need to change
    }

    private void validate(Long profileId) {
        if(!profileRepository.existById(profileId)){
            throw new EntityNotFoundException("Profile Not Found : " + profileId);
        }
    }
}
