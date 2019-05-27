package com.demo.alipay;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {

    private static final String DATABASE_URL = Utils.valueOr(System.getenv("DATABASE_URL"), "jdbc:mysql://rm-p2u21oov2y6xf35533o.mysql.rds.aliyuncs.com:3306/bookinfo");
    private static final String USERNAME = Utils.valueOr(System.getenv("DATABASE_USERNAME"), "root");
    private static final String PASSWORD = Utils.valueOr(System.getenv("DATABASE_PASSWORD"), "Ali_1234");

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(DATABASE_URL);
        config.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useSSL", "false");

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private HikariCPDataSource() {}
}
