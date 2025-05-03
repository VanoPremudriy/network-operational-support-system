package ru.mirea.network.operational.support.system.info.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "ru.mirea.network.operational.support.system.db.entity")
public class DatabaseConfig {
}
