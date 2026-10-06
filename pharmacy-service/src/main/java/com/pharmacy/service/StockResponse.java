package com.pharmacy.service;

public record StockResponse(String drugCode, Integer quantity, boolean allowSale,
                            String source, String message) {}