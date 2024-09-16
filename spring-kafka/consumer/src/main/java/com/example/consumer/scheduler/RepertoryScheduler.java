package com.example.consumer.scheduler;

import com.example.consumer.repository.RepertoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RepertoryScheduler {
    private final RepertoryRepository repository;

    // *s *m *h *d *m *day of week or MON-SUN= * 0/2 *
    @Scheduled(cron = "0 */2 * * * *")
    @Transactional
    public void delete() {
        repository.deleteRepositoryExpirationDateTimeOut(LocalDateTime.now());
    }
}