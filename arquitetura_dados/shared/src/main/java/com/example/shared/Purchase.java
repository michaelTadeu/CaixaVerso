package com.example.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Purchase is our "business event" persisted for analytics.
 * In a real company this could be produced by a payment system.
 */
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "customer_id", nullable = false, length = 64)
    private String customerId;

    @Column(name = "merchant", nullable = false, length = 128)
    private String merchant;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 8)
    private String currency;

    @Column(name = "ts", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime ts;

    protected Purchase() {
        // JPA
    }

    public Purchase(String id, String customerId, String merchant, BigDecimal amount, String currency, OffsetDateTime ts) {
        this.id = id;
        this.customerId = customerId;
        this.merchant = merchant;
        this.amount = amount;
        this.currency = currency;
        this.ts = ts;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getMerchant() { return merchant; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public OffsetDateTime getTs() { return ts; }
}
