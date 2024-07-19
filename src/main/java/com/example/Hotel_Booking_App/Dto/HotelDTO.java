package com.example.Hotel_Booking_App.Dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class HotelDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private double rating;
    private List<RoomDTO> rooms;
}
