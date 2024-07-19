package com.example.Hotel_Booking_App.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String roomType;
    private double price;
    private boolean isBooked;
    private Long hotelId;
}
