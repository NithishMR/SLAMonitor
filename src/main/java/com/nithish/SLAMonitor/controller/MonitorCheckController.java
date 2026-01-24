package com.nithish.SLAMonitor.controller;

import com.nithish.SLAMonitor.DTO.MonitorCheckResponse;
import com.nithish.SLAMonitor.service.MonitorCheckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitors")
public class MonitorCheckController {

    private final MonitorCheckService monitorCheckService;

    public MonitorCheckController(MonitorCheckService monitorCheckService) {
        this.monitorCheckService = monitorCheckService;
    }

    @PostMapping("/{id}/check-now")
    public MonitorCheckResponse checkNow(@PathVariable Long id) {
        return monitorCheckService.runCheckNow(id);
    }
    @GetMapping("/{id}/status")
    public MonitorCheckResponse getMonitorCheckStatus(@PathVariable Long id){
        return monitorCheckService.getMonitorCheckStatus(id);
    }
    @GetMapping("/{id}/history")
    public List<MonitorCheckResponse> getMonitorCheckHistory(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "100") int limit
    ) {
        return monitorCheckService.getMonitorCheckHistory(id, limit);
    }

}
