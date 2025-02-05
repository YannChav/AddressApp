package org.mines.address.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class AppConfiguration {
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.systemDefault());
    }
}