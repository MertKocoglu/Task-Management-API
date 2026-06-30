package com.taskmanagmentapi.service;

import com.taskmanagmentapi.dto.response.DashboardResponse;
import com.taskmanagmentapi.enums.TaskStatus;
import com.taskmanagmentapi.repository.ProjectRepository;
import com.taskmanagmentapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public DashboardResponse getDashboardStats() {
        return DashboardResponse.builder()
                .totalProjects(projectRepository.count())
                .totalTasks(taskRepository.count())
                .todoTasks(taskRepository.countByStatus(TaskStatus.TODO))
                .inProgressTasks(taskRepository.countByStatus(TaskStatus.IN_PROGRESS))
                .completedTasks(taskRepository.countByStatus(TaskStatus.DONE))
                .cancelledTasks(taskRepository.countByStatus(TaskStatus.CANCELLED))
                .overdueTasks(taskRepository.countByDeadlineBeforeAndStatusNot(LocalDate.now(), TaskStatus.DONE))
                .build();
    }
}