package com.example.Hotel_Booking_App.Services;

import com.example.Hotel_Booking_App.Entitys.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserById(Long id);
    User findByUsername(String username);
    List<User> findAllUsers();
}
