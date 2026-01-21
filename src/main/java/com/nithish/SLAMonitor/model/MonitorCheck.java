package com.nithish.SLAMonitor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "monitor_checks")
public class MonitorCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many checks belong to ONE monitor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonitorStatus status;

    private Integer httpStatus;

    private Integer latencyMs;

    private String errorReason;

    @Column(nullable = false)
    private LocalDateTime checkedAt;

    // getters/setters...

    public Long getId() {
        return id;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public MonitorStatus getStatus() {
        return status;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public Integer getLatencyMs() {
        return latencyMs;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }
}
