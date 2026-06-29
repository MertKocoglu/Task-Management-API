package com.taskmanagmentapi.mapper;

import com.taskmanagmentapi.dto.response.TaskResponse;
import com.taskmanagmentapi.entity.Task;

public class TaskMapper {

    public static TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .assignedUserId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null)
                .assignedUserName(task.getAssignedUser() != null
                        ? task.getAssignedUser().getFirstName() + " " + task.getAssignedUser().getLastName()
                        : null)
                .build();
    }
}