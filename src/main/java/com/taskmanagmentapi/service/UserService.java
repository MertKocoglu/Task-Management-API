package com.taskmanagmentapi.service;

import com.taskmanagmentapi.dto.request.CreateUserRequest;
import com.taskmanagmentapi.dto.response.UserResponse;
import com.taskmanagmentapi.entity.User;
import com.taskmanagmentapi.exception.BadRequestException;
import com.taskmanagmentapi.exception.ResourceNotFoundException;
import com.taskmanagmentapi.mapper.UserMapper;
import com.taskmanagmentapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
//toUserDto yapabilirsin
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        return UserMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return UserMapper.toResponse(findUserById(id));
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}