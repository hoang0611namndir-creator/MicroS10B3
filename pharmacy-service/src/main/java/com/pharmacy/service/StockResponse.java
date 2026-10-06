package com.pharmacy.service;

public record StockResponse(String drugCode, Integer quantity, String status, String message) {}