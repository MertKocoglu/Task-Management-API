package com.taskmanagmentapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalProjects;
    private long totalTasks;
    private long todoTasks;
    private long inProgressTasks;
    private long completedTasks;
    private long cancelledTasks;
    private long overdueTasks;
}