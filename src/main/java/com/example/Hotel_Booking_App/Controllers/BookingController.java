package com.example.Hotel_Booking_App.Controllers;

import com.example.Hotel_Booking_App.Dto.BookingDTO;
import com.example.Hotel_Booking_App.Entitys.Booking;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking savedBooking = bookingService.saveBooking(bookingDTO);
        return ResponseEntity.ok(mapperUtil.toBookingDTO(savedBooking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.findBookingById(id);
        return ResponseEntity.ok(mapperUtil.toBookingDTO(booking));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.findAllBookings();
        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(mapperUtil::toBookingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }
}
