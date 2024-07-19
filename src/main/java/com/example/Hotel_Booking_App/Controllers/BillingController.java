package com.example.Hotel_Booking_App.Controllers;

import com.example.Hotel_Booking_App.Dto.BillDTO;
import com.example.Hotel_Booking_App.Entitys.Bill;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bills")
public class BillingController {
    @Autowired
    private BillService billService;

    @Autowired
    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<BillDTO> createBill(@RequestBody Bill bill) {
        Bill savedBill = billService.saveBill(bill);
        return ResponseEntity.ok(mapperUtil.toBillDTO(savedBill));
    }

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<BillDTO> createBillByBookingId(@PathVariable Long bookingId) {
        Bill savedBill = billService.saveBill(bookingId);
        return ResponseEntity.ok(mapperUtil.toBillDTO(savedBill));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDTO> getBillById(@PathVariable Long id) {
        Bill bill = billService.findBillById(id);
        if (bill == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapperUtil.toBillDTO(bill));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BillDTO>> getAllBills() {
        List<Bill> bills = billService.findAllBills();
        List<BillDTO> billDTOs = bills.stream()
                .map(mapperUtil::toBillDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(billDTOs);
    }
}
