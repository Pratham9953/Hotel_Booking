package com.example.Hotel_Booking_App.Repositories;

import com.example.Hotel_Booking_App.Dto.RoomDTO;
import com.example.Hotel_Booking_App.Entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
