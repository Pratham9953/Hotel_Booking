package com.example.Hotel_Booking_App.Services;

import com.example.Hotel_Booking_App.Entitys.Room;

import java.util.List;

public interface RoomService {
    Room saveRoom(Room room);
    Room findRoomById(Long id);
    List<Room> findAllRooms();
}
