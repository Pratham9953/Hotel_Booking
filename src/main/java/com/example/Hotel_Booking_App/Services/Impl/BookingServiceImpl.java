package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Dto.BookingDTO;
import com.example.Hotel_Booking_App.Entitys.Booking;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
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
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Booking saveBooking(BookingDTO bookingDTO) {
        try {
            Room room = roomRepository.findById(bookingDTO.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + bookingDTO.getRoomId()));
            User user = userRepository.findById(bookingDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + bookingDTO.getUserId()));

            room.setBooked(true);
            roomRepository.save(room);

            Booking booking = Booking.builder()
                    .room(room)
                    .user(user)
                    .bookingDate(bookingDTO.getBookingDate())
                    .endTime(bookingDTO.getEndTime())
                    .build();

            return bookingRepository.save(booking);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException to be handled by a global exception handler
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the booking", e);
        }
    }

    @Override
    public Booking findBookingById(Long id) {
        try {
            return bookingRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException to be handled by a global exception handler
        } catch (Exception e) {
            throw new RuntimeException("Failed to find the booking", e);
        }
    }

    @Override
    public List<Booking> findAllBookings() {
        try {
            return bookingRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all bookings", e);
        }
    }

    @Override
    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkAndUpdateBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();
            LocalDateTime now = LocalDateTime.now();
            for (Booking booking : bookings) {
                if (booking.getEndTime().isBefore(now)) {
                    Room room = booking.getRoom();
                    room.setBooked(false);
                    roomRepository.save(room);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to check and update bookings", e);
        }
    }
}
