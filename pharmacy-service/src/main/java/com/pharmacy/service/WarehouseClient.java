package com.pharmacy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class WarehouseClient {

    private final RestTemplate restTemplate;

    @Value("${warehouse.url}")
    private String warehouseUrl;

    public WarehouseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "warehouseCB", fallbackMethod = "fallback")
    public StockResponse checkStock(String drugCode) {
        return restTemplate.getForObject(
                warehouseUrl + "/api/warehouse/stock/" + drugCode, StockResponse.class);
    }

    // Chay khi goi loi HOAC khi mach dang OPEN (fail-fast)
    public StockResponse fallback(String drugCode, Throwable ex) {
        String msg = (ex instanceof CallNotPermittedException)
                ? "Mach dang OPEN - tu choi goi kho tong (fail-fast)"
                : "Kho tong loi: " + ex.getClass().getSimpleName();
        return new StockResponse(drugCode, null, "UNAVAILABLE", msg);
    }
}