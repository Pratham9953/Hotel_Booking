package com.example.Hotel_Booking_App.ControllersTest;

import com.example.Hotel_Booking_App.Controllers.RoomController;
import com.example.Hotel_Booking_App.Dto.RoomDTO;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private RoomController roomController;

    private Room room;
    private RoomDTO roomDTO;

    @BeforeEach
    public void setup() {
        room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        room.setRoomType("Deluxe");
        room.setPrice(200.0);
        // Add other properties if needed

        roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setRoomNumber("101");
        roomDTO.setRoomType("Deluxe");
        roomDTO.setPrice(200.0);
        // Add other properties if needed
    }

    @Test
    public void testCreateRoom() {
        when(roomService.saveRoom(any(Room.class))).thenReturn(room);
        when(mapperUtil.toRoomDTO(any(Room.class))).thenReturn(roomDTO);

        ResponseEntity<RoomDTO> response = roomController.createRoom(room);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDTO.getId(), response.getBody().getId());
        assertEquals(roomDTO.getRoomNumber(), response.getBody().getRoomNumber());
        assertEquals(roomDTO.getRoomType(), response.getBody().getRoomType());
        assertEquals(roomDTO.getPrice(), response.getBody().getPrice());
    }

    @Test
    public void testGetRoomById() {
        when(roomService.findRoomById(anyLong())).thenReturn(room);
        when(mapperUtil.toRoomDTO(any(Room.class))).thenReturn(roomDTO);

        ResponseEntity<RoomDTO> response = roomController.getRoomById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDTO.getId(), response.getBody().getId());
        assertEquals(roomDTO.getRoomNumber(), response.getBody().getRoomNumber());
        assertEquals(roomDTO.getRoomType(), response.getBody().getRoomType());
        assertEquals(roomDTO.getPrice(), response.getBody().getPrice());
    }

    @Test
    public void testGetAllRooms() {
        List<Room> rooms = Arrays.asList(room, room);
        List<RoomDTO> roomDTOs = Arrays.asList(roomDTO, roomDTO);

        when(roomService.findAllRooms()).thenReturn(rooms);
        when(mapperUtil.toRoomDTO(any(Room.class))).thenReturn(roomDTO);

        ResponseEntity<List<RoomDTO>> response = roomController.getAllRooms();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDTOs.size(), response.getBody().size());
        for (int i = 0; i < roomDTOs.size(); i++) {
            assertEquals(roomDTOs.get(i).getId(), response.getBody().get(i).getId());
            assertEquals(roomDTOs.get(i).getRoomNumber(), response.getBody().get(i).getRoomNumber());
            assertEquals(roomDTOs.get(i).getRoomType(), response.getBody().get(i).getRoomType());
            assertEquals(roomDTOs.get(i).getPrice(), response.getBody().get(i).getPrice());
        }
    }
}
