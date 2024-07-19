package com.example.Hotel_Booking_App.Repositories;

import com.example.Hotel_Booking_App.Entitys.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
