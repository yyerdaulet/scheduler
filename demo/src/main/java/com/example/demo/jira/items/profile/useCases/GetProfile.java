package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.group.domain.repository.GroupRepository;
import com.example.demo.jira.items.profile.domain.Profile;
import com.example.demo.jira.items.profile.domain.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;

public class GetProfile {
    private final ProfileRepository profileRepository;

    public GetProfile(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public Profile execute(Long profileId){
        return profileRepository.findById(profileId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Profile Not Found : " + profileId
                        )
                );
    }
}
