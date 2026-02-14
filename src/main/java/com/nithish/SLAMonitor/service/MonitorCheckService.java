package com.nithish.SLAMonitor.service;

import com.nithish.SLAMonitor.DTO.MonitorCheckResponse;
import com.nithish.SLAMonitor.DTO.SlaResponse;
import com.nithish.SLAMonitor.model.Monitor;
import com.nithish.SLAMonitor.model.MonitorCheck;
import com.nithish.SLAMonitor.model.MonitorStatus;
import com.nithish.SLAMonitor.repository.MonitorCheckRepository;
import com.nithish.SLAMonitor.repository.MonitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorCheckService {

    private final MonitorRepository monitorRepository;
    private final MonitorCheckRepository monitorCheckRepository;
    private final WebClient webClient;

    public MonitorCheckService(MonitorRepository monitorRepository,
                               MonitorCheckRepository monitorCheckRepository,
                               WebClient webClient) {
        this.monitorRepository = monitorRepository;
        this.monitorCheckRepository = monitorCheckRepository;
        this.webClient = webClient;
    }

    public MonitorCheckResponse runCheckNow(Long monitorId) {

        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("Monitor not found"));

        LocalDateTime checkedAt = LocalDateTime.now();

        MonitorStatus finalStatus;
        Integer httpStatus = null;
        Integer latencyMs = null;
        String errorReason = null;

        long start = System.currentTimeMillis();

        try {
            Integer statusCode = webClient
                    .get()
                    .uri(monitor.getUrl())
                    .retrieve()
                    .toBodilessEntity()
                    .timeout(Duration.ofMillis(monitor.getTimeoutMs()))
                    .map(res -> res.getStatusCode().value())
                    .block();

            long end = System.currentTimeMillis();
            latencyMs = (int) (end - start);
            httpStatus = statusCode;

            if (statusCode == null) {
                finalStatus = MonitorStatus.DOWN;
                errorReason = "No HTTP status received";
            } else if (!statusCode.equals(monitor.getExpectedStatus())) {
                finalStatus = MonitorStatus.DOWN;
                errorReason = "Expected " + monitor.getExpectedStatus() + " but got " + statusCode;
            } else if (monitor.getLatencyThresholdMs() != null
                    && latencyMs > monitor.getLatencyThresholdMs()) {
                finalStatus = MonitorStatus.DEGRADED;
                errorReason = "Latency exceeded threshold";
            } else {
                finalStatus = MonitorStatus.UP;
            }

        } catch (Exception e) {
            long end = System.currentTimeMillis();
            latencyMs = (int) (end - start);

            finalStatus = MonitorStatus.DOWN;
            errorReason = e.getClass().getSimpleName() + ": " + e.getMessage();
        }

        // Save check history
        MonitorCheck check = new MonitorCheck();
        check.setMonitor(monitor);
        check.setStatus(finalStatus);
        check.setHttpStatus(httpStatus);
        check.setLatencyMs(latencyMs);
        check.setErrorReason(errorReason);
        check.setCheckedAt(checkedAt);

        monitorCheckRepository.save(check);

        // Update monitor lastCheckedAt
        monitor.setLastCheckedAt(checkedAt);
        monitor.setUpdatedAt(LocalDateTime.now());
        monitorRepository.save(monitor);

        // Response DTO
        MonitorCheckResponse response = new MonitorCheckResponse();
        response.setMonitorId(monitor.getId());
        response.setStatus(finalStatus);
        response.setHttpStatus(httpStatus);
        response.setLatencyMs(latencyMs);
        response.setErrorReason(errorReason);
        response.setCheckedAt(checkedAt);

        return response;
    }

    public MonitorCheckResponse getMonitorCheckStatus(Long monitorId) {

        MonitorCheck monitorCheck = monitorCheckRepository
                .findTopByMonitor_IdOrderByCheckedAtDesc(monitorId)
                .orElseThrow(() -> new RuntimeException("No checks found for this monitor"));

        MonitorCheckResponse response = new MonitorCheckResponse();
        response.setCheckedAt(monitorCheck.getCheckedAt());
        response.setStatus(monitorCheck.getStatus());
        response.setMonitorId(monitorCheck.getMonitor().getId());
        response.setHttpStatus(monitorCheck.getHttpStatus());
        response.setLatencyMs(monitorCheck.getLatencyMs());
        response.setErrorReason(monitorCheck.getErrorReason());

        return response;
    }


    public List<MonitorCheckResponse> getMonitorCheckHistory(Long id, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<MonitorCheck> monitorChecks =
                monitorCheckRepository.findByMonitor_IdOrderByCheckedAtDesc(id, pageable);

        List<MonitorCheckResponse> monitorCheckResponses = new ArrayList<>();
        for( MonitorCheck monitorCheck : monitorChecks){
            monitorCheckResponses.add(getMonitorCheckResponse((monitorCheck)));
        }
        return monitorCheckResponses;
    }
    public MonitorCheckResponse getMonitorCheckResponse(MonitorCheck monitorCheck){
        MonitorCheckResponse response = new MonitorCheckResponse();
        response.setCheckedAt(monitorCheck.getCheckedAt());
        response.setStatus(monitorCheck.getStatus());
        response.setMonitorId(monitorCheck.getMonitor().getId());
        response.setHttpStatus(monitorCheck.getHttpStatus());
        response.setLatencyMs(monitorCheck.getLatencyMs());
        response.setErrorReason(monitorCheck.getErrorReason());

        return response;
    }

    public SlaResponse getSLAMetricsForMonitor(Long id, int range) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minusHours(range);

        List<MonitorCheck> monitorChecksWithinRange = monitorCheckRepository.findByMonitor_IdAndCheckedAtAfter(id,startTime);
        int totalChecks = monitorChecksWithinRange.size();
        if(totalChecks == 0){
            SlaResponse slaResponse = new SlaResponse();
            slaResponse.setMonitorId(id);
            slaResponse.setTotalChecks(totalChecks);
            slaResponse.setUpChecks(0);
            slaResponse.setDownChecks(0);
            slaResponse.setUptimePercentage((double)100);
            slaResponse.setRangeHours(range);
            return slaResponse;
        }
        int upChecks = 0;
        for( MonitorCheck monitor : monitorChecksWithinRange){
            MonitorStatus monitorStatus = monitor.getStatus();
            if( monitorStatus == MonitorStatus.UP || monitorStatus == MonitorStatus.DEGRADED){
                upChecks++;
            }
        }
        int downChecks = totalChecks - upChecks;
        double totalUptime = (double) upChecks / totalChecks * 100;
        // total uptime calculation
        //uptime % = (successful checks / total checks) * 100
        SlaResponse slaResponse = new SlaResponse();
        slaResponse.setMonitorId(id);
        slaResponse.setTotalChecks(totalChecks);
        slaResponse.setUpChecks(upChecks);
        slaResponse.setDownChecks(downChecks);
        slaResponse.setUptimePercentage(totalUptime);
        slaResponse.setRangeHours(range);
        return slaResponse;
    }
}
