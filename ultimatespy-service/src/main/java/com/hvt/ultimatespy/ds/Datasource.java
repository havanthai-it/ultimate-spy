package com.hvt.ultimatespy.ds;

import com.hvt.ultimatespy.config.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
        ds.setMinIdle(Integer.parseInt(Config.prop.getProperty("ds.pool.minIdle")));
        ds.setMaxIdle(Integer.parseInt(Config.prop.getProperty("ds.pool.maxIdle")));
        ds.setMaxTotal(Integer.parseInt(Config.prop.getProperty("ds.pool.maxTotal")));
        ds.setInitialSize(Integer.parseInt(Config.prop.getProperty("ds.pool.init")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(Config.prop.getProperty("ds.pool.maxStmt")));
        ds.setMaxWaitMillis(Integer.parseInt(Config.prop.getProperty("ds.pool.timeout")));
        ds.setRemoveAbandonedTimeout(Integer.parseInt(Config.prop.getProperty("ds.pool.abandonedTimeout")));
        ds.setRemoveAbandonedOnBorrow(true);
        ds.setRemoveAbandonedOnMaintenance(true);
        ds.setDefaultAutoCommit(true);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            // logger.info("[Datasouce] getNumActive=" + ds.getNumActive());
            // logger.info("[Datasouce] getNumIdle=" + ds.getNumIdle());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "[DATA SOURCE] Can not value connection", e);
        }
        return conn;
    }

    public static void close(Connection conn, CallableStatement cs, ResultSet rs) {
        if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
        if (cs != null) try { cs.close(); } catch (SQLException ignore) {}
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }
}
