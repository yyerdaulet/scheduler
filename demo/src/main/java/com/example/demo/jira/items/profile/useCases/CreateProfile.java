package com.example.demo.jira.items.profile.useCases;

import com.example.demo.jira.items.profile.domain.model.Profile;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;
import com.example.demo.jira.items.profile.dto.ProfileRequest;
import com.example.demo.jira.items.profile.dto.ProfileResponse;

public class CreateProfile {
    private final ProfileRepository profileRepository;

    public CreateProfile(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public ProfileResponse execute(ProfileRequest profileRequest){
        Profile profile = new Profile(
                profileRequest.userId(),
                profileRequest.name(),
                profileRequest.lastName(),
                profileRequest.localDate(),
                profileRequest.hours(),
                profileRequest.degree(),
                null,
                profileRequest.lectureship()
        );

        Profile savedProfile =  profileRepository.save(profile);

        return new ProfileResponse(
                savedProfile.getId(),
                savedProfile.getName(),
                savedProfile.getLastName(),
                savedProfile.getBirthday(),
                savedProfile.getHours(),
                savedProfile.getDegree(),
                null,
                savedProfile.getLectureship()
        );
    }
}
