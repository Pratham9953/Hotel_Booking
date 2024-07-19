package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Dto.BookingDTO;
import com.example.Hotel_Booking_App.Entitys.Booking;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Repositories.BookingRepository;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Repositories.UserRepository;
import com.example.Hotel_Booking_App.Services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Booking saveBooking(BookingDTO bookingDTO) {
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        room.setBooked(true);
        roomRepository.save(room);

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setEndTime(bookingDTO.getEndTime());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkAndUpdateBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getEndTime().isBefore(now)) {
                Room room = booking.getRoom();
                room.setBooked(false);
                roomRepository.save(room);
            }
        }
    }
}
