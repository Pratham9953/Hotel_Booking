package com.example.Hotel_Booking_App.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor // Required for JPA
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingDate;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public Booking(Long id, LocalDateTime bookingDate, LocalDateTime endTime, User user, Room room) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.endTime = endTime;
        this.user = user;
        this.room = room;
    }
}
