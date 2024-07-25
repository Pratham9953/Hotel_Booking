package com.example.Hotel_Booking_App.Entitys;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor // Required for JPA
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

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL) // Ensures cascading save operations
    private List<Room> rooms;

    @Builder
    public Hotel(Long id, String name, String address, String city, String state, String country, double rating, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.rating = rating;
        this.rooms = rooms;
    }
}
