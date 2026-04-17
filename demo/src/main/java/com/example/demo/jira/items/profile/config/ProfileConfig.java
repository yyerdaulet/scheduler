package com.example.demo.jira.items.profile.config;

import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;
import com.example.demo.jira.items.profile.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfig {

    @Bean
    public GetProfiles getProfiles(ProfileRepository repository){
        return new GetProfiles(repository);
    }

    @Bean
    public GetProfile getProfile(ProfileRepository repository){
        return new GetProfile(repository);
    }

    @Bean
    public CreateProfile createProfile(ProfileRepository repository){
        return new CreateProfile(repository);
    }

    @Bean
    public UpdateProfile updateProfiles(ProfileRepository repository){
        return new UpdateProfile(repository);
    }

    @Bean
    public DeleteProfile deleteProfile(ProfileRepository repository){
        return new DeleteProfile(repository);
    }

}
