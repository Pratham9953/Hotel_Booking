package com.example.Hotel_Booking_App.Services;

import com.example.Hotel_Booking_App.Dto.BookingDTO;
import com.example.Hotel_Booking_App.Entitys.Booking;

import java.util.List;

public interface BookingService {
    Booking saveBooking(BookingDTO bookingDTO);
    Booking findBookingById(Long id);
    List<Booking> findAllBookings();
    void checkAndUpdateBookings();
}
