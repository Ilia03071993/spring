package com.example.audit.controller;

import com.example.model.dto.AuditDto;
import com.example.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping("/log")
    public void logAction(@RequestBody AuditDto auditDto) {
        auditService.actionAudit(auditDto);
    }
}
