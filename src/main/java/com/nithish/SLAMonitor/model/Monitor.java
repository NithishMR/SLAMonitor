package com.nithish.SLAMonitor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monitors")
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HttpMethod method;

    @Column(nullable = false)
    private Integer intervalSeconds;

    @Column(nullable = false)
    private Integer expectedStatus;

    @Column(nullable = false)
    private Integer timeoutMs;

    private Integer latencyThresholdMs;

    @Column(nullable = false)
    private Boolean isActive;

    private LocalDateTime lastCheckedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relationship: One Monitor → Many Checks
    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonitorCheck> checks = new ArrayList<>();

    // getters...

    public Long getId() {
        return id;
    }

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

    public Boolean getActive() {
        return isActive;
    }

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<MonitorCheck> getChecks() {
        return checks;
    }
    //setter

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setChecks(List<MonitorCheck> checks) {
        this.checks = checks;
    }
}
