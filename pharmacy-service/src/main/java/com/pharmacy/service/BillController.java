package com.pharmacy.service;


import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@RequestMapping("/api/v1")
public class BillController {

    @Value("${pharmacy.vat-rate}")
    private BigDecimal vatRate;   // đơn vị %, 5 = 5%

    @PostMapping("/bill")
    public ResponseEntity<?> calculate(@RequestBody BillRequest request) {
        if (request == null || request.items() == null || request.items().isEmpty()) {
            return ResponseEntity.badRequest().body("Hoa don phai co it nhat 1 loai thuoc");
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        for (BillRequest.Item item : request.items()) {
            if (item.price() == null || item.price().signum() < 0 || item.quantity() <= 0) {
                return ResponseEntity.badRequest().body("Gia hoac so luong khong hop le: " + item.name());
            }
            subtotal = subtotal.add(item.price().multiply(BigDecimal.valueOf(item.quantity())));
        }

        BigDecimal vatAmount = subtotal.multiply(vatRate)
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(vatAmount);

        return ResponseEntity.ok(new BillResponse(subtotal, vatRate, vatAmount, total));
    }
}