package com.example.api;

import com.example.shared.CustomerTotal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    private final AnalyticsService analytics;

    @Value("${instance.id:${HOSTNAME:local}}") // INSTANCE_ID env wins, then hostname
    private String instanceId;

    public ApiController(AnalyticsService analytics) {
        this.analytics = analytics;
    }

    @GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "ts", OffsetDateTime.now().toString(),
                "instanceId", instanceId
        );
    }

    @GetMapping(path = "/instance", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> instance() {
        return Map.of("instanceId", instanceId);
    }

    @GetMapping(path = "/analytics/customer-totals", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerTotal> totalsByCustomer() {
        return analytics.totalsByCustomer();
    }

    @GetMapping(path = "/analytics/merchant-totals", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerTotal> totalsByMerchantCached() {
        return analytics.totalsByMerchantCached();
    }
}
