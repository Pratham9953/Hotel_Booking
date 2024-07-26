package com.example.Hotel_Booking_App.ControllersTest;

import com.example.Hotel_Booking_App.Controllers.BookingController;
import com.example.Hotel_Booking_App.Dto.BookingDTO;
import com.example.Hotel_Booking_App.Entitys.Booking;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;
    private BookingDTO bookingDTO;

    @BeforeEach
    public void setup() {
        booking = new Booking();
        booking.setId(1L);
        booking.setBookingDate(null); // set other properties if needed

        bookingDTO = new BookingDTO();
        bookingDTO.setId(1L);
        bookingDTO.setBookingDate(null); // set other properties if needed
    }

    @Test
    public void testCreateBooking() {
        when(bookingService.saveBooking(any(BookingDTO.class))).thenReturn(booking);
        when(mapperUtil.toBookingDTO(any(Booking.class))).thenReturn(bookingDTO);

        ResponseEntity<BookingDTO> response = bookingController.createBooking(bookingDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetBookingById() {
        when(bookingService.findBookingById(anyLong())).thenReturn(booking);
        when(mapperUtil.toBookingDTO(any(Booking.class))).thenReturn(bookingDTO);

        ResponseEntity<BookingDTO> response = bookingController.getBookingById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetAllBookings() {
        List<Booking> bookings = Arrays.asList(booking, booking);
        List<BookingDTO> bookingDTOs = Arrays.asList(bookingDTO, bookingDTO);

        when(bookingService.findAllBookings()).thenReturn(bookings);
        when(mapperUtil.toBookingDTO(any(Booking.class))).thenReturn(bookingDTO);

        ResponseEntity<List<BookingDTO>> response = bookingController.getAllBookings();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingDTOs.size(), response.getBody().size());
    }
}
