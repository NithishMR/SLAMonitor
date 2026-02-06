package com.nithish.SLAMonitor.service;

import com.nithish.SLAMonitor.model.Monitor;
import com.nithish.SLAMonitor.repository.MonitorRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerService {

    private final MonitorRepository monitorRepository;
    private final MonitorCheckService monitorCheckService;

    public SchedulerService(MonitorRepository monitorRepository,
                            MonitorCheckService monitorCheckService) {
        this.monitorRepository = monitorRepository;
        this.monitorCheckService = monitorCheckService;
    }

    // Runs every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void runScheduledChecks() {

        List<Monitor> monitors = monitorRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (Monitor monitor : monitors) {

            // Skip paused monitors
            if (!Boolean.TRUE.equals(monitor.getActive())) {
                continue;
            }

            // If never checked before → run immediately
            if (monitor.getLastCheckedAt() == null) {
                runSafely(monitor);
                continue;
            }

            // Check if interval has passed
            long secondsSinceLastCheck =
                    Duration.between(monitor.getLastCheckedAt(), now).getSeconds();

            if (secondsSinceLastCheck >= monitor.getIntervalSeconds()) {
                runSafely(monitor);
            }
        }
    }

    private void runSafely(Monitor monitor) {
        try {
            monitorCheckService.runCheckNow(monitor.getId());
        } catch (Exception e) {
            // Never let one monitor break the scheduler
            System.err.println(
                    "Scheduler error for monitor " + monitor.getId() + ": " + e.getMessage()
            );
        }
    }
}
