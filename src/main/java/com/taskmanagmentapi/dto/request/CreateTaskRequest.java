package com.taskmanagmentapi.dto.request;

import com.taskmanagmentapi.enums.TaskPriority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskPriority priority;

    @Future
    private LocalDate deadline;

    @NotNull
    private Long projectId;

    private Long assignedUserId;
}