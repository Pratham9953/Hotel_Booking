package com.example.Hotel_Booking_App.ControllersTest;

import com.example.Hotel_Booking_App.Controllers.BillingController;
import com.example.Hotel_Booking_App.Dto.BillDTO;
import com.example.Hotel_Booking_App.Entitys.Bill;
import com.example.Hotel_Booking_App.Mapper.MapperUtil;
import com.example.Hotel_Booking_App.Services.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillingControllerTest {

    @Mock
    private BillService billService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private BillingController billingController;

    private Bill bill;
    private BillDTO billDTO;

    @BeforeEach
    public void setup() {
        bill = new Bill();
        bill.setId(1L);
        bill.setAmount(200.0);
        // set other properties

        billDTO = new BillDTO();
        billDTO.setId(1L);
        billDTO.setAmount(200.0);
        // set other properties
    }

    @Test
    public void testCreateBill() {
        when(billService.saveBill(any(Bill.class))).thenReturn(bill);
        when(mapperUtil.toBillDTO(any(Bill.class))).thenReturn(billDTO);

        ResponseEntity<BillDTO> response = billingController.createBill(bill);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testCreateBillByBookingId() {
        when(billService.saveBill(anyLong())).thenReturn(bill);
        when(mapperUtil.toBillDTO(any(Bill.class))).thenReturn(billDTO);

        ResponseEntity<BillDTO> response = billingController.createBillByBookingId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetBillById() {
        when(billService.findBillById(anyLong())).thenReturn(bill);
        when(mapperUtil.toBillDTO(any(Bill.class))).thenReturn(billDTO);

        ResponseEntity<BillDTO> response = billingController.getBillById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billDTO.getId(), response.getBody().getId());
    }

    @Test
    public void testGetBillById_NotFound() {
        when(billService.findBillById(anyLong())).thenReturn(null);

        ResponseEntity<BillDTO> response = billingController.getBillById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetAllBills() {
        List<Bill> bills = Arrays.asList(bill, bill);
        List<BillDTO> billDTOs = Arrays.asList(billDTO, billDTO);

        when(billService.findAllBills()).thenReturn(bills);
        when(mapperUtil.toBillDTO(any(Bill.class))).thenReturn(billDTO);

        ResponseEntity<List<BillDTO>> response = billingController.getAllBills();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billDTOs.size(), response.getBody().size());
    }
}
