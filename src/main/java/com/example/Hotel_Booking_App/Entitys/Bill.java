package com.example.Hotel_Booking_App.Entitys;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor // Required for JPA
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime issueDate;
    private double amount;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(length = 1000) // Adjust length based on your requirement
    private String description; // Ensure this field is present and annotated correctly

    @Builder
    public Bill(Long id, LocalDateTime issueDate, double amount, Booking booking, String description) {
        this.id = id;
        this.issueDate = issueDate;
        this.amount = amount;
        this.booking = booking;
        this.description = description;
    }
}
