package com.nithish.SLAMonitor.service;

import com.nithish.SLAMonitor.DTO.CreateMonitorRequest;
import com.nithish.SLAMonitor.DTO.MonitorResponse;
import com.nithish.SLAMonitor.model.HttpMethod;
import com.nithish.SLAMonitor.model.Monitor;
import org.springframework.stereotype.Service;
import com.nithish.SLAMonitor.repository.MonitorRepository;

import java.time.LocalDateTime;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorService(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    public MonitorResponse createMonitor(CreateMonitorRequest request) {

        Monitor monitor = new Monitor();

        monitor.setName(request.getName());
        monitor.setUrl(request.getUrl());

        // default method = GET if not provided
        if (request.getMethod() == null) {
            monitor.setMethod(HttpMethod.GET);
        } else {
            monitor.setMethod(request.getMethod());
        }

        monitor.setIntervalSeconds(request.getIntervalSeconds());
        monitor.setExpectedStatus(request.getExpectedStatus());
        monitor.setTimeoutMs(request.getTimeoutMs());
        monitor.setLatencyThresholdMs(request.getLatencyThresholdMs());

        monitor.setActive(true);
        monitor.setCreatedAt(LocalDateTime.now());
        monitor.setUpdatedAt(LocalDateTime.now());

        Monitor saved = monitorRepository.save(monitor);

        //MonitorResponse response = getMonitorResponse(saved);

        return getMonitorResponse(saved);
    }

    private static MonitorResponse getMonitorResponse(Monitor saved) {
        MonitorResponse response = new MonitorResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setUrl(saved.getUrl());
        response.setMethod(saved.getMethod());
        response.setIntervalSeconds(saved.getIntervalSeconds());
        response.setExpectedStatus(saved.getExpectedStatus());
        response.setTimeoutMs(saved.getTimeoutMs());
        response.setLatencyThresholdMs(saved.getLatencyThresholdMs());
        response.setActive(saved.getActive());
        return response;
    }
}
