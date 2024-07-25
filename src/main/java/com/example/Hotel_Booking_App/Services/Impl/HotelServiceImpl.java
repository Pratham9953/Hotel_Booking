package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Hotel;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.HotelRepository;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        try {
            return hotelRepository.save(hotel);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the hotel", e);
        }
    }

    @Override
    public Hotel findHotelById(Long id) {
        try {
            return hotelRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException to be handled by global exception handler
        } catch (Exception e) {
            throw new RuntimeException("Failed to find the hotel", e);
        }
    }

    @Override
    public List<Hotel> findAllHotels() {
        try {
            return hotelRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all hotels", e);
        }
    }

    @Override
    @Transactional
    public Hotel saveHotelWithRooms(Hotel hotel) {
        try {
            Hotel savedHotel = hotelRepository.save(hotel);

            for (Room room : hotel.getRooms()) {
                room.setHotel(savedHotel);
                roomRepository.save(room);
            }

            return savedHotel;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the hotel with rooms", e);
        }
    }
}
