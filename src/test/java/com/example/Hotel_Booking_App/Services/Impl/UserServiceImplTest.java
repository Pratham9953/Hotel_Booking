package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.UserRepository;
import com.example.Hotel_Booking_App.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .email("testuser@example.com")
                .build();
    }

    @Test
    public void testSaveUserSuccess() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserByIdSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    public void testFindByUsernameSuccess() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("testuser"));
    }

    @Test
    public void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCreateUser() {
        User createdUser = userService.createUser("newuser", "newpassword", "newuser@example.com");

        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
        assertEquals("newpassword", createdUser.getPassword());
        assertEquals("newuser@example.com", createdUser.getEmail());
    }

    @Test
    public void testSaveUserException() {
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.saveUser(user));
    }
}
