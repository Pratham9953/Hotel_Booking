package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Room;
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
        return roomRepository.save(room);
    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }
}
