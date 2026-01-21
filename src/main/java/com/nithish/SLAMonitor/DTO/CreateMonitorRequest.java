package com.nithish.SLAMonitor.DTO;

import com.nithish.SLAMonitor.model.HttpMethod;

public class CreateMonitorRequest {

    private String name;
    private String url;
    private HttpMethod method;

    private Integer intervalSeconds;
    private Integer expectedStatus;
    private Integer timeoutMs;
    private Integer latencyThresholdMs; // optional

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public Integer getExpectedStatus() {
        return expectedStatus;
    }

    public Integer getTimeoutMs() {
        return timeoutMs;
    }

    public Integer getLatencyThresholdMs() {
        return latencyThresholdMs;
    }

    // setters (needed for JSON -> DTO mapping)
    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setIntervalSeconds(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public void setExpectedStatus(Integer expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public void setTimeoutMs(Integer timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public void setLatencyThresholdMs(Integer latencyThresholdMs) {
        this.latencyThresholdMs = latencyThresholdMs;
    }
}
