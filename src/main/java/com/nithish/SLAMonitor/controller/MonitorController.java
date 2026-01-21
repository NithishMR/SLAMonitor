package com.nithish.SLAMonitor.controller;

import com.nithish.SLAMonitor.DTO.CreateMonitorRequest;
import com.nithish.SLAMonitor.DTO.MonitorResponse;
import org.springframework.web.bind.annotation.*;
import com.nithish.SLAMonitor.service.MonitorService;

@RestController
@RequestMapping("/api/v1/monitors")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public MonitorResponse createMonitor(@RequestBody CreateMonitorRequest request) {
        System.out.println("create monitor invoked");
        return monitorService.createMonitor(request);
    }
}
