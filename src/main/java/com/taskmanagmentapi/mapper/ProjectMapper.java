package com.taskmanagmentapi.mapper;

import com.taskmanagmentapi.dto.response.ProjectResponse;
import com.taskmanagmentapi.entity.Project;

public class ProjectMapper {

    public static ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .build();
    }
}