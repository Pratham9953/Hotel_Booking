package com.example.Hotel_Booking_App.Services.Impl;

import com.example.Hotel_Booking_App.Entitys.Bill;
import com.example.Hotel_Booking_App.Entitys.Booking;
import com.example.Hotel_Booking_App.Entitys.Room;
import com.example.Hotel_Booking_App.Entitys.User;
import com.example.Hotel_Booking_App.Exceptions.ResourceNotFoundException;
import com.example.Hotel_Booking_App.Repositories.BillRepository;
import com.example.Hotel_Booking_App.Repositories.BookingRepository;
import com.example.Hotel_Booking_App.Repositories.RoomRepository;
import com.example.Hotel_Booking_App.Repositories.UserRepository;
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
public class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BillServiceImpl billService;

    private Bill bill;
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

        booking = Booking.builder()
                .id(1L)
                .room(room)
                .user(user)
                .bookingDate(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(3))
                .build();

        bill = Bill.builder()
                .id(1L)
                .booking(booking)
                .issueDate(LocalDateTime.now())
                .amount(720.0)
                .description("Sample description")
                .build();
    }

    @Test
    public void testSaveBill() {
        when(billRepository.findByBooking_Id(anyLong())).thenReturn(null);
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        Bill savedBill = billService.saveBill(bill);

        assertNotNull(savedBill);
        assertEquals(bill.getId(), savedBill.getId());
        verify(billRepository, times(1)).save(any(Bill.class));
    }

    @Test
    public void testSaveBillWithExistingBill() {
        when(billRepository.findByBooking_Id(anyLong())).thenReturn(bill);

        Bill existingBill = billService.saveBill(bill);

        assertNotNull(existingBill);
        assertEquals(bill.getId(), existingBill.getId());
        verify(billRepository, times(0)).save(any(Bill.class));
    }

    @Test
    public void testSaveBillByBookingId() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(billRepository.findByBooking_Id(anyLong())).thenReturn(null);
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        Bill savedBill = billService.saveBill(1L);

        assertNotNull(savedBill);
        assertEquals(bill.getId(), savedBill.getId());
        verify(billRepository, times(1)).save(any(Bill.class));
    }

    @Test
    public void testSaveBillByBookingIdWithExistingBill() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(billRepository.findByBooking_Id(anyLong())).thenReturn(bill);

        Bill existingBill = billService.saveBill(1L);

        assertNotNull(existingBill);
        assertEquals(bill.getId(), existingBill.getId());
        verify(billRepository, times(0)).save(any(Bill.class));
    }

    @Test
    public void testSaveBillByBookingIdBookingNotFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> billService.saveBill(1L));
    }

    @Test
    public void testFindBillById() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        Bill foundBill = billService.findBillById(1L);

        assertNotNull(foundBill);
        assertEquals(bill.getId(), foundBill.getId());
        verify(billRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindBillByIdNotFound() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> billService.findBillById(1L));
    }

    @Test
    public void testFindAllBills() {
        when(billRepository.findAll()).thenReturn(Arrays.asList(bill));

        List<Bill> bills = billService.findAllBills();

        assertNotNull(bills);
        assertEquals(1, bills.size());
        verify(billRepository, times(1)).findAll();
    }
}
