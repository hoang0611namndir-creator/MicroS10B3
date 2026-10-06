package com.pharmacy.service;

import java.math.BigDecimal;
import java.util.List;

public record BillRequest(List<Item> items) {
    public record Item(String name, BigDecimal price, int quantity) {}
}