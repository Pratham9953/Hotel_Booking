package com.example.Hotel_Booking_App.Mapper;

import com.example.Hotel_Booking_App.Dto.RoomDTO;
import com.example.Hotel_Booking_App.Entitys.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public Room toEntity(RoomDTO dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPrice(dto.getPrice());
        return room;
    }

    public RoomDTO toDto(Room entity) {
        RoomDTO dto = new RoomDTO();
        dto.setId(entity.getId());
        dto.setRoomNumber(entity.getRoomNumber());
        dto.setRoomType(entity.getRoomType());
        dto.setPrice(entity.getPrice());
        dto.setHotelId(entity.getHotel().getId());
        return dto;
    }
}
