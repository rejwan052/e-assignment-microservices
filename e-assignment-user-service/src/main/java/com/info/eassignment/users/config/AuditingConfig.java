package com.info.eassignment.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditingEntityListener createAuditingListener() {
        return new AuditingEntityListener();
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }

}
