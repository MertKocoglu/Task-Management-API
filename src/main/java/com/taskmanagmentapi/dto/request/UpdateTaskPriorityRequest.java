package com.taskmanagmentapi.dto.request;

import com.taskmanagmentapi.enums.TaskPriority;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskPriorityRequest {

    @NotNull
    private TaskPriority priority;
}