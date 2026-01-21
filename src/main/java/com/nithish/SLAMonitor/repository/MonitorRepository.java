package com.nithish.SLAMonitor.repository;

import com.nithish.SLAMonitor.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
}
