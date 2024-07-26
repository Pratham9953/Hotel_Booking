package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Hotel;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.HotelRepository;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Services.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;
    private Room room;

    @BeforeEach
    public void setup() {
        room = Room.builder()
                .id(1L)
                .roomNumber("101")
                .price(200.0)
                .isBooked(false)
                .build();

        hotel = Hotel.builder()
                .id(1L)
                .name("Grand Hotel")
                .address("123 Luxury St")
                .rating(5)
                .rooms(Arrays.asList(room))
                .build();
    }

    @Test
    public void testSaveHotelSuccess() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Hotel savedHotel = hotelService.saveHotel(hotel);

        assertNotNull(savedHotel);
        assertEquals(hotel.getId(), savedHotel.getId());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    public void testFindHotelByIdSuccess() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        Hotel foundHotel = hotelService.findHotelById(1L);

        assertNotNull(foundHotel);
        assertEquals(hotel.getId(), foundHotel.getId());
        verify(hotelRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindHotelByIdNotFound() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelService.findHotelById(1L));
    }

    @Test
    public void testFindAllHotels() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel));

        List<Hotel> hotels = hotelService.findAllHotels();

        assertNotNull(hotels);
        assertEquals(1, hotels.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void testSaveHotelWithRoomsSuccess() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Hotel savedHotel = hotelService.saveHotelWithRooms(hotel);

        assertNotNull(savedHotel);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    public void testSaveHotelWithRoomsException() {
        when(hotelRepository.save(any(Hotel.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> hotelService.saveHotelWithRooms(hotel));
    }
}
