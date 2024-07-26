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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingDTO bookingDTO;
    private Booking booking;
    private Room room;
    private User user;

    @BeforeEach
    public void setup() {
        room = Room.builder()
                .id(1L)
                .roomNumber("101")
                .price(200.0)
                .isBooked(false)
                .build();

        user = User.builder()
                .id(1L)
                .username("JohnDoe")
                .password("password")
                .email("john@example.com")
                .build();

        bookingDTO = new BookingDTO();
        bookingDTO.setRoomId(1L);
        bookingDTO.setUserId(1L);
        bookingDTO.setBookingDate(LocalDateTime.now());
        bookingDTO.setEndTime(LocalDateTime.now().plusDays(3));

        booking = Booking.builder()
                .id(1L)
                .room(room)
                .user(user)
                .bookingDate(bookingDTO.getBookingDate())
                .endTime(bookingDTO.getEndTime())
                .build();
    }

    @Test
    public void testSaveBookingSuccess() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Booking savedBooking = bookingService.saveBooking(bookingDTO);

        assertNotNull(savedBooking);
        assertEquals(booking.getId(), savedBooking.getId());
        assertTrue(room.isBooked());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testSaveBookingRoomNotFound() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.saveBooking(bookingDTO));
    }

    @Test
    public void testSaveBookingUserNotFound() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.saveBooking(bookingDTO));
    }

    @Test
    public void testFindBookingById() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.findBookingById(1L);

        assertNotNull(foundBooking);
        assertEquals(booking.getId(), foundBooking.getId());
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindBookingByIdNotFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.findBookingById(1L));
    }

    @Test
    public void testFindAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));

        List<Booking> bookings = bookingService.findAllBookings();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void testCheckAndUpdateBookings() {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        Booking pastBooking = Booking.builder()
                .id(2L)
                .room(room)
                .user(user)
                .bookingDate(pastDateTime.minusDays(2))
                .endTime(pastDateTime)
                .build();

        room.setBooked(true);
        List<Booking> bookings = Arrays.asList(booking, pastBooking);

        when(bookingRepository.findAll()).thenReturn(bookings);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        bookingService.checkAndUpdateBookings();

        assertFalse(room.isBooked());
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testCheckAndUpdateBookingsException() {
        when(bookingRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> bookingService.checkAndUpdateBookings());
    }
}
