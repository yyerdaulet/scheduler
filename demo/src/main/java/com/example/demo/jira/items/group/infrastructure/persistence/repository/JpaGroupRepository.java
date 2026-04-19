package com.example.demo.jira.items.group.infrastructure.persistence.repository;

import com.example.demo.jira.items.group.infrastructure.persistence.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupRepository extends JpaRepository<GroupEntity,Long> {

}
