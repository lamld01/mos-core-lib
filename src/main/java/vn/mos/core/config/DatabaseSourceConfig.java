package vn.mos.core.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import vn.mos.core.properties.DatabaseConfig;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatabaseConfig.class)
@RequiredArgsConstructor
@Log4j2
public class DatabaseSourceConfig {

    private final DatabaseConfig properties;

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClassName());
        return dataSource;
    }

    @PostConstruct
    public void logConfig() {
      log.info("Using Database URL: {}", properties.getUrl());
    }
}
