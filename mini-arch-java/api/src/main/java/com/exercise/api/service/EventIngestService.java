package com.exercise.api.service;

import com.exercise.api.model.EventIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.Map;

@Service
public class EventIngestService {

  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${app.inboxFile}")
  private String inboxFile;

  public void ingest(EventIn in) throws Exception {
    if (in.getType() == null || in.getType().isBlank()) {
      throw new IllegalArgumentException("type é obrigatório");
    }
    long ts = (in.getTs() != null) ? in.getTs() : Instant.now().toEpochMilli();

    Map<String, Object> event = Map.of(
        "type", in.getType(),
        "source", (in.getSource() == null ? "unknown" : in.getSource()),
        "ts", ts
    );

    Path file = Paths.get(inboxFile);
    Files.createDirectories(file.getParent());

    String line = mapper.writeValueAsString(event) + "\n";
    Files.writeString(file, line, StandardCharsets.UTF_8,
        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
  }
}
