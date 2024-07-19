package com.example.Hotel_Booking_App.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {
        private Long id;
        private LocalDateTime bookingDate;
        private LocalDateTime endTime;
        private Long userId;
        private Long roomId;
        private UserDTO user;
        private RoomDTO room;
}
