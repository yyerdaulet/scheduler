package com.example.demo.jira.items.profile.domain.repository;

import com.example.demo.jira.items.profile.domain.model.Profile;
import com.example.demo.jira.items.profile.infrastucture.persistence.ProfileMapper;
import com.example.demo.jira.items.profile.infrastucture.persistence.JpaProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository{
    private final JpaProfileRepository jpa;
    private final ProfileMapper mapper;

    @Override
    public List<Profile> findAll(){
        return jpa.findAll()
                .stream().map(
                        mapper::toDomain
                ).toList();
    }

    @Override
    public Optional<Profile> findById(Long profileId){
        return jpa.findById(profileId).map(mapper::toDomain);
    }

    @Override
    public Profile save(Profile profile){
        return mapper.toDomain(jpa.save(mapper.toNewEntity(profile)));
    }

    @Override
    public Boolean existById(Long profileId){
        return jpa.existsById(profileId);
    }

    @Override
    public void delete(Long profileId){
        jpa.deleteById(profileId);
    }
}
