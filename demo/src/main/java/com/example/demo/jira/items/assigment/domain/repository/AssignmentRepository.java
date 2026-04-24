package com.example.demo.jira.items.assigment.domain.repository;

import com.example.demo.jira.items.assigment.domain.model.Assignment;

import java.util.List;

public interface AssignmentRepository {
    Assignment save(Assignment assignment);

    List<Assignment> findAll();
}
