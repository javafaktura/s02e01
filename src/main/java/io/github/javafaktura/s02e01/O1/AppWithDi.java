package io.github.javafaktura.s02e01.O1;

import io.github.javafaktura.s02e01.logic.SomeRepositoryImpl;
import io.github.javafaktura.s02e01.logic.SomeService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

class AppWithDi {

    public static void main(String[] args) {

        var dbUrl = "jdbc:mysql://localhost:3306/springcore";
        var dbUsername = "user";
        var dbPass = "admin123";

        var jdbcTemplate = configureJdbcTemplate(dbUrl, dbUsername, dbPass);
        var repo = new SomeRepositoryImpl(jdbcTemplate);
        var service = new SomeService(repo);

        service.mostPopularValue()
                .ifPresent(System.out::println);
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
}

