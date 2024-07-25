package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.UserRepository;
import com.example.Hotel_Booking_App.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the user", e);
        }
    }

    @Override
    public User findUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException to be handled by a global exception handler
        } catch (Exception e) {
            throw new RuntimeException("Failed to find the user", e);
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException to be handled by a global exception handler
        } catch (Exception e) {
            throw new RuntimeException("Failed to find the user by username", e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all users", e);
        }
    }

    // Example of using the Builder pattern to create a User object
    public User createUser(String username, String password, String email) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
