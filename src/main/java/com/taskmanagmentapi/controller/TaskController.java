package com.taskmanagmentapi.controller;

import com.taskmanagmentapi.dto.request.AssignTaskRequest;
import com.taskmanagmentapi.dto.request.CreateTaskRequest;
import com.taskmanagmentapi.dto.request.UpdateTaskPriorityRequest;
import com.taskmanagmentapi.dto.request.UpdateTaskStatusRequest;
import com.taskmanagmentapi.dto.response.TaskResponse;
import com.taskmanagmentapi.enums.TaskPriority;
import com.taskmanagmentapi.enums.TaskStatus;
import com.taskmanagmentapi.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long assignedUserId
    ) {
        return taskService.filterTasks(status, priority, projectId, assignedUserId);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/assign")
    public TaskResponse assignTask(@PathVariable Long id, @Valid @RequestBody AssignTaskRequest request) {
        return taskService.assignTask(id, request);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateStatus(id, request);
    }

    @PatchMapping("/{id}/priority")
    public TaskResponse updatePriority(@PathVariable Long id, @Valid @RequestBody UpdateTaskPriorityRequest request) {
        return taskService.updatePriority(id, request);
    }
}