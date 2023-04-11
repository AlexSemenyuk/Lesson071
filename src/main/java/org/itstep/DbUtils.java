package org.itstep;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DbUtils {
    private DataSource dataSource;

    private final static DbUtils instance = new DbUtils();

    private DbUtils() {

    }

    public static DbUtils getInstance() {
        return instance;
    }

    public void init(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }

    public Optional<Connection> getConnection() throws SQLException {
        return Optional.ofNullable(dataSource != null ? dataSource.getConnection() : null);
    }
}
