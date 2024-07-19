package com.example.Hotel_Booking_App.Services;

import com.example.Hotel_Booking_App.Entitys.Bill;

import java.util.List;

public interface BillService {
    Bill saveBill(Bill bill);
    Bill saveBill(Long bookingId); // New method
    Bill findBillById(Long id);
    List<Bill> findAllBills();
}
