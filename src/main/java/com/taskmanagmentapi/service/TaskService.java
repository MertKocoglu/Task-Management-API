package com.taskmanagmentapi.service;

import com.taskmanagmentapi.dto.request.AssignTaskRequest;
import com.taskmanagmentapi.dto.request.CreateTaskRequest;
import com.taskmanagmentapi.dto.request.UpdateTaskPriorityRequest;
import com.taskmanagmentapi.dto.request.UpdateTaskStatusRequest;
import com.taskmanagmentapi.dto.response.TaskResponse;
import com.taskmanagmentapi.entity.Project;
import com.taskmanagmentapi.entity.Task;
import com.taskmanagmentapi.entity.User;
import com.taskmanagmentapi.enums.TaskPriority;
import com.taskmanagmentapi.enums.TaskStatus;
import com.taskmanagmentapi.exception.BadRequestException;
import com.taskmanagmentapi.exception.ResourceNotFoundException;
import com.taskmanagmentapi.mapper.TaskMapper;
import com.taskmanagmentapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public TaskResponse createTask(CreateTaskRequest request) {
        Project project = projectService.findProjectById(request.getProjectId());

        User assignedUser = null;
        if (request.getAssignedUserId() != null) {
            assignedUser = userService.findUserById(request.getAssignedUserId());
            validateUserActiveTaskLimit(assignedUser.getId());
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .deadline(request.getDeadline())
                .project(project)
                .assignedUser(assignedUser)
                .status(TaskStatus.TODO)
                .build();

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        return TaskMapper.toResponse(findTaskById(id));
    }

    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    public TaskResponse assignTask(Long taskId, AssignTaskRequest request) {
        Task task = findTaskById(taskId);

        if (task.getStatus() == TaskStatus.CANCELLED) {
            throw new BadRequestException("Cancelled task cannot be assigned");
        }

        User user = userService.findUserById(request.getUserId());
        validateUserActiveTaskLimit(user.getId());

        task.setAssignedUser(user);

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updateStatus(Long taskId, UpdateTaskStatusRequest request) {
        Task task = findTaskById(taskId);

        if (task.getStatus() == TaskStatus.DONE && request.getStatus() == TaskStatus.TODO) {
            throw new BadRequestException("Done task cannot be moved back to TODO");
        }

        if (request.getStatus() == TaskStatus.DONE
                && task.getDeadline() != null
                && task.getDeadline().isBefore(LocalDate.now())) {
            throw new BadRequestException("Task with expired deadline cannot be marked as DONE");
        }

        task.setStatus(request.getStatus());

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updatePriority(Long taskId, UpdateTaskPriorityRequest request) {
        Task task = findTaskById(taskId);
        task.setPriority(request.getPriority());

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public List<TaskResponse> filterTasks(TaskStatus status, TaskPriority priority, Long projectId, Long assignedUserId) {
        if (status != null) {
            return taskRepository.findByStatus(status).stream().map(TaskMapper::toResponse).toList();
        }

        if (priority != null) {
            return taskRepository.findByPriority(priority).stream().map(TaskMapper::toResponse).toList();
        }

        if (projectId != null) {
            return taskRepository.findByProjectId(projectId).stream().map(TaskMapper::toResponse).toList();
        }

        if (assignedUserId != null) {
            return taskRepository.findByAssignedUserId(assignedUserId).stream().map(TaskMapper::toResponse).toList();
        }

        return getAllTasks();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private void validateUserActiveTaskLimit(Long userId) {
        long activeTaskCount = taskRepository.countByAssignedUserIdAndStatusNot(userId, TaskStatus.DONE);

        if (activeTaskCount >= 20) {
            throw new BadRequestException("User cannot have more than 20 active tasks");
        }
    }
}