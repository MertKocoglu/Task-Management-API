package com.taskmanagmentapi.mapper;

import com.taskmanagmentapi.dto.response.UserResponse;
import com.taskmanagmentapi.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
