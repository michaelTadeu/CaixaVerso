package com.exercise.api.controller;

import com.exercise.api.model.EventIn;
import com.exercise.api.service.EventIngestService;
import com.exercise.api.service.StatsReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ApiController {

  private final EventIngestService ingest;
  private final StatsReadService stats;

  public ApiController(EventIngestService ingest, StatsReadService stats) {
    this.ingest = ingest;
    this.stats = stats;
  }

  @PostMapping("/api/events")
  public ResponseEntity<?> postEvent(@RequestBody EventIn event) {
    try {
      ingest.ingest(event);
      return ResponseEntity.accepted().body(Map.of("status", "queued"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", "Falha ao gravar evento", "detail", e.getMessage()));
    }
  }

  @GetMapping("/api/stats")
  public Map<String, Object> getStats() {
    return stats.readStats();
  }

  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status", "ok", "service", "api");
  }
}
