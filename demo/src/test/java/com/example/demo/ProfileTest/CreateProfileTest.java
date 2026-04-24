package com.example.demo.ProfileTest;

import com.example.demo.jira.TaskManagerApplication;
import com.example.demo.jira.items.profile.Enum.Degree;
import com.example.demo.jira.items.profile.domain.repository.ProfileRepository;
import com.example.demo.jira.items.profile.dto.ProfileRequest;
import com.example.demo.jira.items.profile.dto.ProfileResponse;
import com.example.demo.jira.items.profile.useCases.CreateProfile;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(classes = TaskManagerApplication.class)
public class CreateProfileTest {
    private final ProfileRepository profileRepository;

    @Autowired
    public CreateProfileTest(ProfileRepository repository){
        this.profileRepository = repository;
    }

    @Test
    public void properDataRequest(){
        CreateProfile useCase = new CreateProfile(profileRepository);

        ProfileRequest request = new ProfileRequest(
                10L,
                "Yerdaulet",
                "Yerbolat",
                LocalDate.of(2005,11,19),
                40L,
                Degree.DOCTOR,
                true
        );

        ProfileResponse response = useCase.execute(request);

        assert response.lectureship().equals(true);
        assert response.degree().equals(Degree.DOCTOR);
        assert response.hours().equals(40L);
        assert response.birthday().equals(LocalDate.of(2005,11,19));
        assert response.lastName().equals("Yerbolat");
        assert response.name().equals("Yerdaulet");
    }
}
