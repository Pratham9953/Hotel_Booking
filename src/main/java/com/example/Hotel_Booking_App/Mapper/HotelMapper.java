package com.example.Hotel_Booking_App.Mapper;

import com.example.Hotel_Booking_App.Dto.HotelDTO;
import com.example.Hotel_Booking_App.Entitys.Hotel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HotelMapper {
    private final RoomMapper roomMapper;

    public HotelMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public Hotel toEntity(HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setId(dto.getId());
        hotel.setName(dto.getName());
        hotel.setAddress(dto.getAddress());
        hotel.setCity(dto.getCity());
        hotel.setState(dto.getState());
        hotel.setCountry(dto.getCountry());
        hotel.setRating(dto.getRating());
        hotel.setRooms(dto.getRooms().stream().map(roomMapper::toEntity).collect(Collectors.toList()));
        return hotel;
    }

    public HotelDTO toDto(Hotel entity) {
        HotelDTO dto = new HotelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setRating(entity.getRating());
        dto.setRooms(entity.getRooms().stream().map(roomMapper::toDto).collect(Collectors.toList()));
        return dto;
    }
}
