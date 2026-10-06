package com.pharmacy.warehouse;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarehouseController {

    // ok | error | slow
    private static volatile String mode = "ok";

    @GetMapping("/api/warehouse/stock/{drugCode}")
    public ResponseEntity<?> stock(@PathVariable String drugCode) throws InterruptedException {
        if ("error".equals(mode)) {
            return ResponseEntity.status(500).body("Warehouse error");
        }
        if ("slow".equals(mode)) {
            Thread.sleep(5000);
        }
        return ResponseEntity.ok(Map.of(
                "drugCode", drugCode,
                "quantity", 100,
                "status", "OK",
                "message", "Con hang"));
    }

    @PostMapping("/admin/mode")
    public String setMode(@RequestParam String value) {
        mode = value;
        return "mode=" + mode;
    }
}
