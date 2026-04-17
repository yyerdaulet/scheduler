package com.example.demo.jira.items.subject.services;

import com.example.demo.jira.items.subject.dto.SubjectCreateRequest;
import com.example.demo.jira.items.subject.dto.SubjectResponse;
import com.example.demo.jira.items.subject.dto.SubjectUpdateRequest;
import com.example.demo.jira.items.subject.enties.SubjectEntity;
import com.example.demo.jira.items.subject.mapper.SubjectMapper;
import com.example.demo.jira.items.subject.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;


    public List<SubjectResponse> getAllSubjects() {
         return subjectRepository.findAll()
                 .stream().map(
                         subjectMapper::toDomain
                 ).toList();
    }


    public SubjectResponse getSubject(Long subjectId) {
        return subjectMapper.toDomain(findSubjectById(subjectId));
    }

    private SubjectEntity findSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                 .orElseThrow(
                         () -> new EntityNotFoundException("Subject Not found : " + subjectId)
                 );
    }

    public SubjectResponse createSubject(SubjectCreateRequest request) {
        SubjectEntity subject = subjectMapper.toEntity(request);
        SubjectEntity savedSubject = subjectRepository.save(subject);

        return subjectMapper.toDomain(savedSubject);
    }




    public SubjectResponse updateSubject(SubjectUpdateRequest request, Long subjectId) {
        SubjectEntity subject = findSubjectById(subjectId);

        subject.setName(request.name());
        subject.setCredits(request.credits());
        subject.setHours(request.hours());
        subject.setType(request.type());

        return subjectMapper.toDomain(subject);
    }

    public void deleteSubject(Long subjectId) {
        existById(subjectId);
        subjectRepository.deleteById(subjectId);
    }

    private void existById(Long subjectId) {
        if(subjectRepository.existsById(subjectId)){
            throw new EntityNotFoundException("Subject not found");
        }
    }


}
