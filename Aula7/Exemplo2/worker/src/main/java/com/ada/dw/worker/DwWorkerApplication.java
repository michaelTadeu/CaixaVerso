package com.ada.dw.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DwWorkerApplication {
  public static void main(String[] args) {
    SpringApplication.run(DwWorkerApplication.class, args);
  }
}
