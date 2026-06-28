package com.taskmanagmentapi.repository;

import com.taskmanagmentapi.entity.Task;
import com.taskmanagmentapi.enums.TaskPriority;
import com.taskmanagmentapi.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByAssignedUserId(Long assignedUserId);

    List<Task> findByProjectId(Long projectId);

    long countByStatus(TaskStatus status);

    long countByDeadlineBeforeAndStatusNot(LocalDate date, TaskStatus status);

    long countByAssignedUserIdAndStatusNot(Long userId, TaskStatus status);
}