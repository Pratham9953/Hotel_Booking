package com.example.Hotel_Booking_App.Entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private String roomType;
    private double price;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
