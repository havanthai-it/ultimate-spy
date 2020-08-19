package com.hvt.ultimatespy.ds;

import com.hvt.ultimatespy.config.Config;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Datasource {

    private static final Logger logger = Logger.getLogger(DataSource.class.getName());
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(Config.prop.getProperty("ds.url"));
        ds.setUsername(Config.prop.getProperty("ds.user"));
        ds.setPassword(Config.prop.getProperty("ds.password"));
        ds.setMinIdle(Integer.parseInt(Config.prop.getProperty("ds.pool.min")));
        ds.setMaxIdle(Integer.parseInt(Config.prop.getProperty("ds.pool.max")));
        ds.setInitialSize(Integer.parseInt(Config.prop.getProperty("ds.pool.init")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(Config.prop.getProperty("ds.pool.maxStmt")));
        ds.setDefaultAutoCommit(true);
        ds.setRemoveAbandoned(true);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "[DATA SOURCE] Can not get connection", e);
        }
        return conn;
    }
}
