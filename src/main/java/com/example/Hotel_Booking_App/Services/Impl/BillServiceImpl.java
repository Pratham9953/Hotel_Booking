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
import com.example.Hotel_Booking_App.Services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Bill saveBill(Bill bill) {
        // Check if a bill with the corresponding booking ID already exists
        Long bookingId = bill.getBooking().getId();
        Bill existingBill = billRepository.findByBooking_Id(bookingId);
        if (existingBill != null) {
            return existingBill; // Return the existing bill if found
        }

        return billRepository.save(bill); // Save the new bill if no existing bill found
    }

    @Override
    public Bill saveBill(Long bookingId) {
        // Fetch booking details using booking ID
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        // Check if a bill with the corresponding booking ID already exists
        Bill existingBill = billRepository.findByBooking_Id(bookingId);
        if (existingBill != null) {
            return existingBill; // Return the existing bill if found
        }

        // Fetch room details using room ID from the booking
        Room room = booking.getRoom();

        // Fetch user details using user ID from the booking
        User user = booking.getUser();

        // Calculate booking duration in days
        long bookingTime = ChronoUnit.DAYS.between(booking.getBookingDate(), booking.getEndTime());

        // Calculate the total bill amount
        double roomPrice = room.getPrice();
        double bookingAmount = bookingTime * roomPrice;
        double tax = bookingAmount * 0.18; // 18% tax
        double totalAmount = bookingAmount + tax;

        // Generate bill description
        String billDescription = String.format("Thanks %s for booking room no: %s. The total bill is %.2f = %.2f (booking time: %d days * room price: %.2f) + 18%% tax and charges.",
                user.getUsername(), room.getRoomNumber(), totalAmount, bookingAmount, bookingTime, roomPrice);

        // Create a new Bill object
        Bill bill = new Bill();
        bill.setBooking(booking);
        bill.setIssueDate(LocalDateTime.now());
        bill.setAmount(totalAmount);
        bill.setDescription(billDescription);

        // Save the bill to the repository
        return billRepository.save(bill);
    }

    @Override
    public Bill findBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }
}
