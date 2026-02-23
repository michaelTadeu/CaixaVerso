package com.example.api;

import com.example.shared.CustomerTotal;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final PurchaseRepository repo;

    public AnalyticsService(PurchaseRepository repo) {
        this.repo = repo;
    }

    public List<CustomerTotal> totalsByCustomer() {
        return repo.totalsByCustomer();
    }

    /**
     * Cacheable endpoint demo: caches merchant totals for a short TTL.
     */
    @Cacheable(cacheNames = "merchantTotals")
    public List<CustomerTotal> totalsByMerchantCached() {
        return repo.totalsByMerchant();
    }
}
