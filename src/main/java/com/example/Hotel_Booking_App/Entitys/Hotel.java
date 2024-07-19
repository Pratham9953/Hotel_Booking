package com.example.Hotel_Booking_App.Entitys;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private double rating;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

}
