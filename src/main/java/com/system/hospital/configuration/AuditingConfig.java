package com.system.hospital.configuration;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditingConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}
