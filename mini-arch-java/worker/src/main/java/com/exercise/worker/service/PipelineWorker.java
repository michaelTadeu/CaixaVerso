package com.exercise.worker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

@Service
public class PipelineWorker {

  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${app.inboxFile}")
  private String inboxFile;

  @Value("${app.offsetFile}")
  private String offsetFile;

  @Value("${app.statsFile}")
  private String statsFile;

  @Scheduled(fixedDelayString = "${worker.intervalMs}")
  public void tick() {
    try {
      Path inbox = Paths.get(inboxFile);
      Files.createDirectories(inbox.getParent());
      if (!Files.exists(inbox)) return;

      long offset = readOffset();
      List<String> lines = Files.readAllLines(inbox, StandardCharsets.UTF_8);
      if (offset >= lines.size()) return;

      List<String> newLines = lines.subList((int) offset, lines.size());
      if (newLines.isEmpty()) return;

      Map<String, Integer> byTypeNew = new LinkedHashMap<>();
      int totalNew = 0;

      for (String line : newLines) {
        if (line == null || line.isBlank()) continue;
        Map<String, Object> ev = mapper.readValue(line, Map.class);
        String type = String.valueOf(ev.getOrDefault("type", "unknown"));
        byTypeNew.put(type, byTypeNew.getOrDefault(type, 0) + 1);
        totalNew++;
      }

      Map<String, Object> merged = mergeIntoExistingStats(totalNew, byTypeNew);

      Path stats = Paths.get(statsFile);
      Files.createDirectories(stats.getParent());
      Files.writeString(stats,
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(merged),
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

      writeOffset(lines.size());
      System.out.println("[worker] processed=" + totalNew + " | offset=" + lines.size());
    } catch (Exception e) {
      System.err.println("[worker] ERROR: " + e.getMessage());
    }
  }

  private long readOffset() {
    try {
      Path f = Paths.get(offsetFile);
      if (!Files.exists(f)) return 0;
      String s = Files.readString(f).trim();
      if (s.isBlank()) return 0;
      return Long.parseLong(s);
    } catch (Exception e) {
      return 0;
    }
  }

  private void writeOffset(long value) throws Exception {
    Path f = Paths.get(offsetFile);
    Files.createDirectories(f.getParent());
    Files.writeString(f, String.valueOf(value), StandardCharsets.UTF_8,
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> mergeIntoExistingStats(int totalNew, Map<String, Integer> byTypeNew) {
    int total = 0;
    Map<String, Integer> byType = new LinkedHashMap<>();

    try {
      Path stats = Paths.get(statsFile);
      if (Files.exists(stats)) {
        Map<String, Object> existing = mapper.readValue(Files.readString(stats), Map.class);
        Object totalObj = existing.get("total");
        if (totalObj instanceof Number n) total = n.intValue();

        Object byTypeObj = existing.get("byType");
        if (byTypeObj instanceof Map<?, ?> m) {
          for (var e : m.entrySet()) {
            String k = String.valueOf(e.getKey());
            Object v = e.getValue();
            if (v instanceof Number n2) byType.put(k, n2.intValue());
          }
        }
      }
    } catch (Exception ignored) {}

    total += totalNew;
    for (var e : byTypeNew.entrySet()) {
      byType.put(e.getKey(), byType.getOrDefault(e.getKey(), 0) + e.getValue());
    }

    return new LinkedHashMap<>(Map.of(
        "total", total,
        "byType", byType,
        "lastUpdated", Instant.now().toString()
    ));
  }
}
