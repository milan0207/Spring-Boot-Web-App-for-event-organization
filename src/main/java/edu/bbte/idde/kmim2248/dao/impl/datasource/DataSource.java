package edu.bbte.idde.kmim2248.dao.impl.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    public DataSource() {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/eventdb");
        config.setUsername("root");
        config.setPassword("1234");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
