package com.pharmacy.service;

import java.math.BigDecimal;

public record BillResponse(BigDecimal subtotal, BigDecimal vatRate,
                           BigDecimal vatAmount, BigDecimal totalAmount) {}