package io.github.javafaktura.s02e01.O2;

import io.github.javafaktura.s02e01.logic.SomeRepository;
import io.github.javafaktura.s02e01.logic.SomeRepositoryImpl;
import io.github.javafaktura.s02e01.logic.SomeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
class ServiceConfig {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPass;
    private final String dbDriverClass;

    ServiceConfig(
            @Value("${jdbc.url}") String dbUrl,
            @Value("${jdbc.username}") String dbUsername,
            @Value("${jdbc.password}") String dbPass,
            @Value("${jdbc.driverClass}") String dbDriverClass) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPass = dbPass;
        this.dbDriverClass = dbDriverClass;
    }

    @Bean
    SomeService someService(SomeRepository repo) {
        return new SomeService(repo);
    }

    @Bean
    SomeRepository repository(JdbcTemplate jdbcTemplate) {
        return new SomeRepositoryImpl(jdbcTemplate);
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    DataSource dataSource() {
        var ds = new DriverManagerDataSource();
        ds.setUrl(dbUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPass);
        ds.setDriverClassName(dbDriverClass);
        return ds;
    }
}
