package com.nithish.SLAMonitor.repository;

import com.nithish.SLAMonitor.model.MonitorCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonitorCheckRepository extends JpaRepository<MonitorCheck, Long> {
    Optional<MonitorCheck> findTopByMonitor_IdOrderByCheckedAtDesc(Long monitorId);

    List<MonitorCheck> findByMonitor_IdOrderByCheckedAtDesc(Long monitorId, Pageable pageable);

    List<MonitorCheck> findByMonitor_IdAndCheckedAtAfter(Long id, LocalDateTime startTime);
}
