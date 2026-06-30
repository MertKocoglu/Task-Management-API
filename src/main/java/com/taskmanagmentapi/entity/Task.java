package com.taskmanagmentapi.entity;

import com.taskmanagmentapi.enums.TaskPriority;
import com.taskmanagmentapi.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate deadline;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //Bu task 1 kullanıcıya atanabilir ve bir kullanıcı birden fazla task alabilir. Bu nedenle ManyToOne ilişkisi kullanıyoruz. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    //Hiçbir task projesiz olamaz. Bu nedenle nullable = false olarak ayarlıyoruz.
    private Project project;

    @PrePersist
    public void prePersist() {
        createdAt = NOW;
        updatedAt = NOW;

        if (status == null) {
            status = TaskStatus.TODO;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = NOW;
    }
}