package com.nithish.SLAMonitor.controller;

import com.nithish.SLAMonitor.DTO.CreateMonitorRequest;
import com.nithish.SLAMonitor.DTO.MonitorResponse;
import com.nithish.SLAMonitor.DTO.UpdateMonitorRequest;
import org.springframework.web.bind.annotation.*;
import com.nithish.SLAMonitor.service.MonitorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitors")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public MonitorResponse createMonitor(@RequestBody CreateMonitorRequest request) {
        return monitorService.createMonitor(request);
    }
    @GetMapping
    public List<MonitorResponse> getMonitors(){
        return monitorService.getMonitors();
    }
    @GetMapping("/{id}")
    public MonitorResponse getMonitor(@PathVariable Long id){
        return monitorService.getMonitor(id);
    }
    @DeleteMapping("/{id}")
    public MonitorResponse deleteMonitor(@PathVariable Long id){
        return monitorService.deleteMonitor(id);
    }
    @PatchMapping("/{id}")
    public MonitorResponse updateMonitor(@RequestBody UpdateMonitorRequest request, @PathVariable Long id){
        return monitorService.updateMonitor(request,id);
    }
}
