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
        return new Clock() {
            @Override
            public ZoneId getZone() {
                return null;
            }

            @Override
            public Clock withZone(ZoneId zoneId) {
                return null;
            }

            @Override
            public Instant instant() {
                return null;
            }
        };
    }
}