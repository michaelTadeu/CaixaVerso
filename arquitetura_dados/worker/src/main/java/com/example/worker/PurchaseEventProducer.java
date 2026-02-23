package com.example.worker;

import com.example.shared.Purchase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class PurchaseEventProducer {

    private final PurchaseRepository repo;
    private final Random random = new Random();

    private final List<String> customers = List.of("c001", "c002", "c003", "c004");
    private final List<String> merchants = List.of("Amazon", "iFood", "Netflix", "Uber", "Spotify");

    public PurchaseEventProducer(PurchaseRepository repo) {
        this.repo = repo;
    }

    @Scheduled(fixedDelayString = "${worker.rate-ms:500}")
    public void produce() {
        String id = UUID.randomUUID().toString();
        String customerId = customers.get(random.nextInt(customers.size()));
        String merchant = merchants.get(random.nextInt(merchants.size()));
        BigDecimal amount = BigDecimal.valueOf(10 + (490 * random.nextDouble()))
                .setScale(2, RoundingMode.HALF_UP);

        Purchase p = new Purchase(
                id,
                customerId,
                merchant,
                amount,
                "BRL",
                OffsetDateTime.now(ZoneOffset.UTC)
        );

        repo.save(p);
        System.out.println("[worker] inserted purchase id=" + id + " customer=" + customerId + " merchant=" + merchant + " amount=" + amount);
    }
}
