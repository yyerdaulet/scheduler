package com.example.demo.jira.items.subject.domain.repository;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.infrastructure.persistence.JpaSubjectRepository;
import com.example.demo.jira.items.subject.infrastructure.persistence.SubjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository{
    private JpaSubjectRepository jpa;
    private SubjectMapper mapper;

    @Override
    public List<Subject> findAll(){
        return jpa.findAll()
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Subject> findById(Long subjectId){
        return jpa.findById(subjectId).map(mapper::toDomain);
    }

    @Override
    public Subject save(Subject subject){
        return mapper.toDomain(
                jpa.save(mapper.toEntity(subject))
        );
    }

    @Override
    public void delete(Long subjectId){
        jpa.deleteById(subjectId);
    }

    @Override
    public Boolean existById(Long subjectId) {
        return jpa.existsById(subjectId);
    }
}
