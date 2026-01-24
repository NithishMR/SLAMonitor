package com.nithish.SLAMonitor.DTO;

import com.nithish.SLAMonitor.model.MonitorStatus;

import java.time.LocalDateTime;

public class MonitorCheckResponse {
    private Long monitorId;
    private MonitorStatus status;
    private Integer httpStatus;
    private Integer latencyMs;
    private String errorReason;
    private LocalDateTime checkedAt;

    public Long getMonitorId() { return monitorId; }
    public void setMonitorId(Long monitorId) { this.monitorId = monitorId; }

    public MonitorStatus getStatus() { return status; }
    public void setStatus(MonitorStatus status) { this.status = status; }

    public Integer getHttpStatus() { return httpStatus; }
    public void setHttpStatus(Integer httpStatus) { this.httpStatus = httpStatus; }

    public Integer getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Integer latencyMs) { this.latencyMs = latencyMs; }

    public String getErrorReason() { return errorReason; }
    public void setErrorReason(String errorReason) { this.errorReason = errorReason; }

    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime checkedAt) { this.checkedAt = checkedAt; }
}
