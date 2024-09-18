package com.example.audit.service;

import com.example.model.dto.AuditDto;
import com.example.audit.entity.Audit;
import com.example.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository repository;

    public void actionAudit(AuditDto auditDto) {
        Audit audit = new Audit();
        audit.setActionType(auditDto.actionType());
        audit.setActionTime(LocalDateTime.now());
        audit.setUserId(auditDto.userId());
        audit.setDetails(auditDto.details());


        repository.save(audit);
    }
}