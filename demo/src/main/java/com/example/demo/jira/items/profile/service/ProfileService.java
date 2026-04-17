package com.example.demo.jira.items.profile.service;

import com.example.demo.jira.integrations.fileStorage.ProfileFileResponse;
import com.example.demo.jira.integrations.fileStorage.FileStorageService;
import com.example.demo.jira.items.profile.dto.ProfileCrReq;
import com.example.demo.jira.items.profile.dto.ProfileCreateResponse;
import com.example.demo.jira.items.profile.dto.ProfileResponse;
import com.example.demo.jira.log.LogExecutionTime;
import com.example.demo.jira.items.profile.model.ProfileEntity;
import com.example.demo.jira.items.profile.mapper.ProfileMapper;
import com.example.demo.jira.items.profile.repo.JpaProfileRepository;
import com.example.demo.jira.authentication.entity.UserEntity;
import com.example.demo.jira.authentication.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;


@AllArgsConstructor
@Service
public class ProfileService {
    private final JpaProfileRepository repository;
    private final UserRepository userRepository;
    private final ProfileMapper mapper;
    private final FileStorageService fileService;
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @LogExecutionTime
    public List<ProfileResponse> getAllProfiles() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @LogExecutionTime
    public List<ProfileResponse> search(String query){
        return repository.findAll(ProfileSpecification.search(query))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }



    @LogExecutionTime()
    public ProfileResponse getProfileById(Long id) {
        ProfileEntity profile = repository
                .findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Student Entity not found"));
        return mapper.toDomain(profile);
    }

    @Transactional
    @LogExecutionTime()
    public ProfileCreateResponse createProfile(@Valid ProfileCrReq profileToCreate) {
        UserEntity user = userRepository.findById(profileToCreate.user_id()).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        var newProfile = new ProfileEntity(
                        null,
                        profileToCreate.name(),
                        profileToCreate.lastName(),
                        profileToCreate.birthday(),
                        profileToCreate.hours(),
                        profileToCreate.degree(),
                        user,
                        null,
                        profileToCreate.lectureship()
                );
        repository.save(newProfile);


        return mapper.toProfileCreateResponse(newProfile);
    }



    @Transactional
    @LogExecutionTime()
    public void deleteProfile(Long id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException("Profile not found ");
        }
        UserEntity user = userRepository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("User not found")
                        );
        user.setProfile(null);
        userRepository.save(user);
        repository.deleteById(id);

    }




    public ProfileFileResponse uploadProfileMedicalPage(Long id, MultipartFile file) throws IOException {
        ProfileEntity profile = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student not found")
        );

        String fileName = fileService.saveFile(file);

        repository.save(profile);

        return mapper.toProfileFileResponse(id,fileName);
    }


    public List<ProfileResponse> getProfilesByLabId(Long labId) {
        List<ProfileEntity> profiles = repository.findByLabId(labId);
        return profiles.stream()
                .map(
                        mapper::toDomain
                ).toList();
    }

    public static  <C extends Collection<String>> C fetchORCID(Collection<ProfileResponse> profiles,
                                                        Collector<String,?,C> collector){
        return profiles.stream()
                .map(ProfileResponse::orcid)
                .collect(collector);
    }
}
