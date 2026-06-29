package com.taskmanagmentapi.service;

import com.taskmanagmentapi.dto.request.CreateProjectRequest;
import com.taskmanagmentapi.dto.response.ProjectResponse;
import com.taskmanagmentapi.entity.Project;
import com.taskmanagmentapi.exception.ResourceNotFoundException;
import com.taskmanagmentapi.mapper.ProjectMapper;
import com.taskmanagmentapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectResponse createProject(CreateProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return ProjectMapper.toResponse(projectRepository.save(project));
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::toResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        return ProjectMapper.toResponse(findProjectById(id));
    }

    public ProjectResponse updateProject(Long id, CreateProjectRequest request) {
        Project project = findProjectById(id);
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        return ProjectMapper.toResponse(projectRepository.save(project));
    }

    public void deleteProject(Long id) {
        Project project = findProjectById(id);
        projectRepository.delete(project);
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }
}