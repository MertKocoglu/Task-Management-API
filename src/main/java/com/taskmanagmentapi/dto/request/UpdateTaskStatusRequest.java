package com.taskmanagmentapi.dto.request;

import com.taskmanagmentapi.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskStatusRequest {

    @NotNull
    private TaskStatus status;
}