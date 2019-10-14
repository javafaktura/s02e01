package io.github.javafaktura.s02e01.logic;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SomeRepositoryImpl implements SomeRepository {

    private final JdbcTemplate jdbcTemplate;

    public SomeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> fetchData() {
        return jdbcTemplate.query(
                "SELECT val from tab",
                new Object[0],
                (rs, rowNum) -> rs.getString("val")
        );
    }
}
