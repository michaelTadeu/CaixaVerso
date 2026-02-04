package com.exercise.api.model;

public class EventIn {
  private String type;
  private String source;
  private Long ts;

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  public String getSource() { return source; }
  public void setSource(String source) { this.source = source; }

  public Long getTs() { return ts; }
  public void setTs(Long ts) { this.ts = ts; }
}
