package com.taskmanagmentapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignTaskRequest {

    @NotNull
    private Long userId;
}