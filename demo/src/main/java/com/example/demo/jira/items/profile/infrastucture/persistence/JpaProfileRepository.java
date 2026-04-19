package com.example.demo.jira.items.profile.infrastucture.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface JpaProfileRepository extends JpaRepository<ProfileEntity,Long>, JpaSpecificationExecutor<ProfileEntity> {
}
