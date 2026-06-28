package com.taskmanagmentapi.dto.response;

import com.taskmanagmentapi.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
}