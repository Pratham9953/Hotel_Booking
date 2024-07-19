package com.example.Hotel_Booking_App.Repositories;

import com.example.Hotel_Booking_App.Entitys.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
