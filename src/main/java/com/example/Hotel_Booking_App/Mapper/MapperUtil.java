package com.example.Hotel_Booking_App.Mapper;

import com.example.Hotel_Booking_App.Dto.*;
import com.example.Hotel_Booking_App.Entitys.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperUtil {

    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public HotelDTO toHotelDTO(Hotel hotel) {
        if (hotel == null) return null;
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setAddress(hotel.getAddress());
        dto.setCity(hotel.getCity());
        dto.setState(hotel.getState());
        dto.setCountry(hotel.getCountry());
        dto.setRating(hotel.getRating());
        dto.setRooms(hotel.getRooms().stream().map(this::toRoomDTO).collect(Collectors.toList()));
        return dto;
    }

    public RoomDTO toRoomDTO(Room room) {
        if (room == null) return null;
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setPrice(room.getPrice());
        dto.setBooked(room.isBooked());
        dto.setHotelId(room.getHotel().getId()); // Assuming there's a hotel reference in RoomDTO
        return dto;
    }

    public BookingDTO toBookingDTO(Booking booking) {
        if (booking == null) return null;
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setEndTime(booking.getEndTime());
        dto.setUser(toUserDTO(booking.getUser()));
        dto.setRoom(toRoomDTO(booking.getRoom()));
        dto.setUserId(booking.getUser().getId());
        dto.setRoomId(booking.getRoom().getId());
        return dto;
    }

    public BillDTO toBillDTO(Bill bill) {
        if (bill == null) return null;
        BillDTO billDTO = new BillDTO();
        billDTO.setId(bill.getId());
        billDTO.setIssueDate(bill.getIssueDate());
        billDTO.setAmount(bill.getAmount());
        billDTO.setDescription(bill.getDescription()); // Ensure description is mapped
        billDTO.setBooking(toBookingDTO(bill.getBooking())); // Map the associated booking if needed
        return billDTO;
    }

    public Hotel toHotelEntity(HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setId(dto.getId());
        hotel.setName(dto.getName());
        hotel.setAddress(dto.getAddress());
        hotel.setCity(dto.getCity());
        hotel.setState(dto.getState());
        hotel.setCountry(dto.getCountry());
        hotel.setRating(dto.getRating());
        hotel.setRooms(dto.getRooms().stream().map(this::toRoomEntity).collect(Collectors.toList()));
        return hotel;
    }

    public Room toRoomEntity(RoomDTO dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPrice(dto.getPrice());
        room.setBooked(dto.isBooked());
        return room;
    }
}
