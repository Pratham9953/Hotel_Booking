package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Hotel;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Repositories.HotelRepository;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel findHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    @Transactional
    public Hotel saveHotelWithRooms(Hotel hotel) {
        Hotel savedHotel = hotelRepository.save(hotel);

        for (Room room : hotel.getRooms()) {
            room.setHotel(savedHotel);
            roomRepository.save(room);
        }

        return savedHotel;
    }
}
