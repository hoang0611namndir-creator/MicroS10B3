package com.pharmacy.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class WarehouseClient {

    private final RestTemplate restTemplate;

    // Ton kho cuc bo (bo nho dem): du lieu mau + cap nhat moi khi goi kho tong thanh cong
    private final Map<String, Integer> localStock = new ConcurrentHashMap<>(Map.of(
            "P001", 50,
            "P002", 30,
            "P003", 20));

    @Value("${warehouse.url}")
    private String warehouseUrl;

    public WarehouseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "warehouseCB", fallbackMethod = "checkWarehouseFallback")
    public StockResponse checkStock(String drugCode) {
        Map<?, ?> res = restTemplate.getForObject(
                warehouseUrl + "/api/warehouse/stock/" + drugCode, Map.class);

        int qty = ((Number) res.get("quantity")).intValue();
        localStock.put(drugCode, qty);   // luu vao bo nho dem cho lan sau

        return new StockResponse(drugCode, qty, qty > 0, "WAREHOUSE", "Ton kho tu kho tong");
    }

    // Chay khi goi kho loi, cham, HOAC khi mach dang OPEN
    public StockResponse checkWarehouseFallback(String drugCode, Exception e) {
        String message = "Không thể kết nối kho tổng. "
                + "Hệ thống sẽ sử dụng dữ liệu tồn kho cục bộ để tiếp tục giao dịch";

        Integer qty = localStock.get(drugCode);
        if (qty == null) {
            return new StockResponse(drugCode, null, false, "LOCAL",
                    message + ". Chưa có dữ liệu cục bộ cho thuốc này, vui lòng kiểm tra tồn kho thủ công");
        }
        return new StockResponse(drugCode, qty, qty > 0, "LOCAL", message);
    }
}