package com.ada.dw.worker.etl;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EtlController {

  private final EtlService etlService;

  public EtlController(EtlService etlService) {
    this.etlService = etlService;
  }

  @PostMapping("/jobs/etl/run")
  public Map<String, Object> run() {
    int affected = etlService.runOnce();
    return Map.of("status", "OK", "rowsAffected", affected);
  }
}
