package com.example.shared;

import java.math.BigDecimal;

/**
 * DTO used by API to return aggregated totals.
 */
public record CustomerTotal(String customerId, BigDecimal totalAmount) {}
