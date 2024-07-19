package com.example.Hotel_Booking_App.Entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime issueDate;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(length = 1000) // Adjust length based on your requirement
    private String description; // Ensure this field is present and annotated correctly

}
