package com.example.Hotel_Booking_App.Repositories;

import com.example.Hotel_Booking_App.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
