package com.example.Hotel_Booking_App.Controllers;

import com.example.Hotel_Booking_App.Dto.HotelDTO;
import com.example.Hotel_Booking_App.Entitys.Hotel;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        // Convert DTO to entity
        Hotel hotel = mapperUtil.toHotelEntity(hotelDTO);

        // Save hotel with rooms
        Hotel savedHotel = hotelService.saveHotelWithRooms(hotel);

        // Convert saved entity back to DTO
        HotelDTO savedHotelDTO = mapperUtil.toHotelDTO(savedHotel);

        return ResponseEntity.ok(savedHotelDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.findHotelById(id);
        return ResponseEntity.ok(mapperUtil.toHotelDTO(hotel));
    }

    @GetMapping("/all")
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<Hotel> hotels = hotelService.findAllHotels();
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(mapperUtil::toHotelDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hotelDTOs);
    }
}
