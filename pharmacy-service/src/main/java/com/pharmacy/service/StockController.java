package com.pharmacy.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StockController {

    private final WarehouseClient warehouseClient;

    public StockController(WarehouseClient warehouseClient) {
        this.warehouseClient = warehouseClient;
    }

    @GetMapping("/stock/{drugCode}")
    public StockResponse stock(@PathVariable String drugCode) {
        return warehouseClient.checkStock(drugCode);
    }
}