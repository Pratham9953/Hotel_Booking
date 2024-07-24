package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room saveRoom(Room room) {
        try {
            return roomRepository.save(room);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the room", e);
        }
    }

    @Override
    public Room findRoomById(Long id) {
        try {
            return roomRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException to be handled by a global exception handler
        } catch (Exception e) {
            throw new RuntimeException("Failed to find the room", e);
        }
    }

    @Override
    public List<Room> findAllRooms() {
        try {
            return roomRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all rooms", e);
        }
    }
}
