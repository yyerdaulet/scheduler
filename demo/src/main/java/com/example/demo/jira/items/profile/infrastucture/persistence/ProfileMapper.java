package com.example.demo.jira.items.profile.infrastucture.persistence;

import com.example.demo.jira.authentication.entity.UserEntity;
import com.example.demo.jira.authentication.repository.UserRepository;
import com.example.demo.jira.items.profile.domain.model.Profile;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileMapper {
    private final UserRepository userRepository;

    public Profile toDomain(ProfileEntity profile){
        return new Profile(
                profile.getId(),
                profile.getName(),
                profile.getLastName(),
                profile.getBirthday(),
                profile.getHours(),
                profile.getDegree(),
                profile.getProfileImage(),
                profile.getLectureship()
        );
    }


    public ProfileEntity toNewEntity(Profile profile) {
        UserEntity user = userRepository.findById(profile.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User Not Found" + profile.getId())
                );
        return new ProfileEntity(
                null,
                profile.getName(),
                profile.getLastName(),
                profile.getBirthday(),
                profile.getHours(),
                profile.getDegree(),
                user,
                profile.getProfileImage(),
                profile.getLectureship()
        );
    }
}
