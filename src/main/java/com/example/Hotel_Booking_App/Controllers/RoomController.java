package com.example.Hotel_Booking_App.Controllers;
import com.example.Hotel_Booking_App.Dto.RoomDTO;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody Room room) {
        Room savedRoom = roomService.saveRoom(room);
        return ResponseEntity.ok(mapperUtil.toRoomDTO(savedRoom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        Room room = roomService.findRoomById(id);
        return ResponseEntity.ok(mapperUtil.toRoomDTO(room));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<Room> rooms = roomService.findAllRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(mapperUtil::toRoomDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDTOs);
    }
}
