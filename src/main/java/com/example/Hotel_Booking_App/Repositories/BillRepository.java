package com.example.Hotel_Booking_App.Repositories;

import com.example.Hotel_Booking_App.Entitys.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findByBooking_Id(Long bookingId);
}