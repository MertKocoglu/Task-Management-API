package com.taskmanagmentapi.repository;

import com.taskmanagmentapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}