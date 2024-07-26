package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Services.RoomService;
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
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room room;

    @BeforeEach
    public void setup() {
        room = Room.builder()
                .id(1L)
                .roomNumber("101")
                .price(200.0)
                .isBooked(false)
                .build();
    }

    @Test
    public void testSaveRoomSuccess() {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room savedRoom = roomService.saveRoom(room);

        assertNotNull(savedRoom);
        assertEquals(room.getId(), savedRoom.getId());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    public void testFindRoomByIdSuccess() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

        Room foundRoom = roomService.findRoomById(1L);

        assertNotNull(foundRoom);
        assertEquals(room.getId(), foundRoom.getId());
        verify(roomRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindRoomByIdNotFound() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomService.findRoomById(1L));
    }

    @Test
    public void testFindAllRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));

        List<Room> rooms = roomService.findAllRooms();

        assertNotNull(rooms);
        assertEquals(1, rooms.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void testSaveRoomException() {
        when(roomRepository.save(any(Room.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> roomService.saveRoom(room));
    }
}
