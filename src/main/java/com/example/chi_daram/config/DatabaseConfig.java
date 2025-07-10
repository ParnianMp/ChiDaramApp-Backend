package com.example.chi_daram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.chi_daram.repository")
public class DatabaseConfig {
}

