package com.example.Hotel_Booking_App.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BillDTO {
    private Long id;
    private LocalDateTime issueDate;
    private double amount;
    private BookingDTO booking;
    private String description;
}
