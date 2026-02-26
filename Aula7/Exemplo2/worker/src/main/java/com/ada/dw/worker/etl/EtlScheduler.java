package com.ada.dw.worker.etl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EtlScheduler {

  private final EtlService etlService;

  public EtlScheduler(EtlService etlService) {
    this.etlService = etlService;
  }

  @Scheduled(cron = "${etl.schedule.cron}")
  public void scheduledRun() {
    etlService.runOnce();
  }
}
