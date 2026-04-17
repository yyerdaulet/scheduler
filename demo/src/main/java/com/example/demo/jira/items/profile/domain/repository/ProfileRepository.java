package com.example.demo.jira.items.profile.domain.repository;

import com.example.demo.jira.items.profile.domain.model.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {
    List<Profile> findAll();
    Optional<Profile> findById(Long profileId);
    Profile save(Profile profile);
    Boolean existById(Long profileId);
    void delete(Long profileId);


}
