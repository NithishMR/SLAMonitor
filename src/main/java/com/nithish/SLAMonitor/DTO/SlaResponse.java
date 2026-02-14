package com.nithish.SLAMonitor.DTO;

public class SlaResponse {
    private Long monitorId;
    private int rangeHours;
    private int totalChecks;
    private int upChecks;
    private int downChecks;
    private double uptimePercentage;

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public int getRangeHours() {
        return rangeHours;
    }

    public void setRangeHours(int rangeHours) {
        this.rangeHours = rangeHours;
    }

    public int getTotalChecks() {
        return totalChecks;
    }

    public void setTotalChecks(int totalChecks) {
        this.totalChecks = totalChecks;
    }

    public int getUpChecks() {
        return upChecks;
    }

    public void setUpChecks(int upChecks) {
        this.upChecks = upChecks;
    }

    public int getDownChecks() {
        return downChecks;
    }

    public void setDownChecks(int downChecks) {
        this.downChecks = downChecks;
    }

    public double getUptimePercentage() {
        return uptimePercentage;
    }

    public void setUptimePercentage(double uptimePercentage) {
        this.uptimePercentage = uptimePercentage;
    }
}
