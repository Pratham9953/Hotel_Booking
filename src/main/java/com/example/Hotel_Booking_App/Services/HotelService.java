package com.example.Hotel_Booking_App.Services;

import com.example.Hotel_Booking_App.Entitys.Hotel;

import java.util.List;

public interface HotelService {
    Hotel saveHotel(Hotel hotel);
    Hotel findHotelById(Long id);
    List<Hotel> findAllHotels();
    Hotel saveHotelWithRooms(Hotel hotel);
}
