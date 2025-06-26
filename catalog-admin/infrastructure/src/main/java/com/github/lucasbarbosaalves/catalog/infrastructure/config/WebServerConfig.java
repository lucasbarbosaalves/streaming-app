package com.github.lucasbarbosaalves.catalog.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.github.lucasbarbosaalves.catalog",
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "com\\.github\\.lucasbarbosaalves\\.catalog\\.infrastructure\\.category\\.dto\\..*")
)
public class WebServerConfig {
}