package com.taskmanagmentapi.service;

import com.taskmanagmentapi.entity.Task;
import com.taskmanagmentapi.enums.TaskStatus;
import com.taskmanagmentapi.exception.BadRequestException;
import com.taskmanagmentapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void assignTask_cancelledTask_throwException() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.CANCELLED);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(BadRequestException.class, () -> {
            taskService.assignTask(1L, requestWithUserId(1L));
        });
    }

    private com.taskmanagmentapi.dto.request.AssignTaskRequest requestWithUserId(Long userId) {
        com.taskmanagmentapi.dto.request.AssignTaskRequest request =
                new com.taskmanagmentapi.dto.request.AssignTaskRequest();
        request.setUserId(userId);
        return request;
    }
}