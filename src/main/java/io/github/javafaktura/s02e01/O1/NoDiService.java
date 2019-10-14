package io.github.javafaktura.s02e01.O1;

import io.github.javafaktura.s02e01.logic.SomeRepository;
import io.github.javafaktura.s02e01.logic.SomeRepositoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map.Entry;
import java.util.Optional;

import static java.util.Map.Entry.comparingByValue;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class NoDiService {

    private final SomeRepository repository;

    NoDiService(String dbUrl, String dbUsername, String dbPass) {
        var jdbcTemplate = configureJdbcTemplate(dbUrl, dbUsername, dbPass);
        this.repository = new SomeRepositoryImpl(jdbcTemplate);
    }

    private static JdbcTemplate configureJdbcTemplate(String dbUrl, String dbUsername, String dbPass) {
        var dataSource = configureDataSource(dbUrl, dbUsername, dbPass);
        return new JdbcTemplate(dataSource);
    }

    private static DataSource configureDataSource(String url, String username, String password) {
        var ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        return ds;
    }

    Optional<String> mostPopularValue() {
        return repository.fetchData().stream()
                .collect(collectingAndThen(
                        groupingBy(identity(), counting()),
                        map -> map.entrySet().stream()
                                .max(comparingByValue())
                                .map(Entry::getKey)));
    }
}
