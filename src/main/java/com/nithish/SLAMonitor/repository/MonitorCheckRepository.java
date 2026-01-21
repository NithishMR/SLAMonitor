package com.nithish.SLAMonitor.repository;

import com.nithish.SLAMonitor.model.MonitorCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorCheckRepository extends JpaRepository<MonitorCheck, Long> {
}
