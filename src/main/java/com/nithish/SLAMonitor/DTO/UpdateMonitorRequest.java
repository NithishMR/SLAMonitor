package com.nithish.SLAMonitor.DTO;

public class UpdateMonitorRequest {
    private Integer intervalSeconds;
    private Integer expectedStatus;
    private Integer timeoutMs;
    private Integer latencyThresholdMs;
    private Boolean active;

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public Integer getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(Integer expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public Integer getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(Integer timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public Integer getLatencyThresholdMs() {
        return latencyThresholdMs;
    }

    public void setLatencyThresholdMs(Integer latencyThresholdMs) {
        this.latencyThresholdMs = latencyThresholdMs;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
