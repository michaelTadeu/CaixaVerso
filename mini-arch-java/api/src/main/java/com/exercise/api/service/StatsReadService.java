package com.exercise.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class StatsReadService {

  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${app.statsFile}")
  private String statsFile;

  @SuppressWarnings("unchecked")
  public Map<String, Object> readStats() {
    try {
      Path file = Paths.get(statsFile);
      if (!Files.exists(file)) {
        return Map.of("total", 0, "byType", Map.of(), "lastUpdated", null);
      }
      return mapper.readValue(Files.readString(file), LinkedHashMap.class);
    } catch (Exception e) {
      return Map.of("error", "Falha ao ler stats.json", "detail", e.getMessage());
    }
  }
}
