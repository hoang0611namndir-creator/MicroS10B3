package com.pharmacy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StockController {

    private final WarehouseClient warehouseClient;

    public StockController(WarehouseClient warehouseClient) {
        this.warehouseClient = warehouseClient;
    }

    @GetMapping("/stock/{drugCode}")
    public ResponseEntity<StockResponse> stock(@PathVariable String drugCode) {
        StockResponse r = warehouseClient.checkStock(drugCode);
        return "UNAVAILABLE".equals(r.status())
                ? ResponseEntity.status(503).body(r)
                : ResponseEntity.ok(r);
    }
}