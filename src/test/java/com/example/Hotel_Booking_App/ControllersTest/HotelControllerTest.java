package com.example.Hotel_Booking_App.ControllersTest;

import com.example.Hotel_Booking_App.Controllers.HotelController;
import com.example.Hotel_Booking_App.Dto.HotelDTO;
import com.example.Hotel_Booking_App.Entitys.Hotel;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.HotelService;
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
public class HotelControllerTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private HotelController hotelController;

    private Hotel hotel;
    private HotelDTO hotelDTO;

    @BeforeEach
    public void setup() {
        hotel = new Hotel();
        hotel.setId(1L);
        // set other properties if needed

        hotelDTO = new HotelDTO();
        hotelDTO.setId(1L);
        // set other properties if needed
    }

    @Test
    public void testCreateHotel() {
        when(mapperUtil.toHotelEntity(any(HotelDTO.class))).thenReturn(hotel);
        when(hotelService.saveHotelWithRooms(any(Hotel.class))).thenReturn(hotel);
        when(mapperUtil.toHotelDTO(any(Hotel.class))).thenReturn(hotelDTO);

        ResponseEntity<HotelDTO> response = hotelController.createHotel(hotelDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotelDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetHotelById() {
        when(hotelService.findHotelById(anyLong())).thenReturn(hotel);
        when(mapperUtil.toHotelDTO(any(Hotel.class))).thenReturn(hotelDTO);

        ResponseEntity<HotelDTO> response = hotelController.getHotelById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotelDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetAllHotels() {
        List<Hotel> hotels = Arrays.asList(hotel, hotel);
        List<HotelDTO> hotelDTOs = Arrays.asList(hotelDTO, hotelDTO);

        when(hotelService.findAllHotels()).thenReturn(hotels);
        when(mapperUtil.toHotelDTO(any(Hotel.class))).thenReturn(hotelDTO);

        ResponseEntity<List<HotelDTO>> response = hotelController.getAllHotels();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotelDTOs.size(), response.getBody().size());
    }
}
