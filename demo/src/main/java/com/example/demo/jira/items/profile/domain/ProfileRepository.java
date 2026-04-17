package com.example.demo.jira.items.profile.domain;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {
    List<Profile> findAll();
    Optional<Profile> findById(Long profileId);
    Profile save(Profile profile);
    void delete(Long profileId);

}
