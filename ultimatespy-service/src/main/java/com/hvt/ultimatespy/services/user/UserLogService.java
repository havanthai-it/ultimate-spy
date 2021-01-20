package com.hvt.ultimatespy.services.user;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.user.UserLog;
import com.hvt.ultimatespy.models.user.UserSubscription;
import com.hvt.ultimatespy.utils.enums.ActionEnum;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserLogService {

    private static final Logger logger = Logger.getLogger(UserLogService.class.getName());

    public CompletableFuture<List<UserLog>> list(String userId, String action, int hours) {
        return CompletableFuture.supplyAsync(() -> {
            List<UserLog> list = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * " +
                    " FROM tb_user_log " +
                    " WHERE s_user_id = ? " +
                    "       AND s_action = ? " +
                    "       AND d_create > DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL ? HOUR) ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, action);
                cs.setInt(3, hours);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    list.add(bindUserLog(rs));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return list;
        });
    }

    public CompletableFuture<Integer> total(String userId, String action, int hours) {
        return CompletableFuture.supplyAsync(() -> {
            Integer total = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*)" +
                    " FROM tb_user_log " +
                    " WHERE s_user_id = ? " +
                    "       AND s_action = ? " +
                    "       AND d_create > DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL ? HOUR) ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, action);
                cs.setInt(3, hours);
                rs = cs.executeQuery();

                if (rs != null && rs.next()) {
                    total = rs.getInt(1);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return total;
        });
    }

    public CompletableFuture<Integer> insert(String userId, String action) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "INSERT INTO " +
                    " tb_user_log(S_USER_ID, S_ACTION, D_CREATE) " +
                    " VALUES(?,?, CURRENT_TIMESTAMP())";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, action);
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
                return 0;
            } finally {
                Datasource.close(conn, cs, null);
            }
            return 1;
        });
    }

    private static UserLog bindUserLog(ResultSet rs) throws SQLException {
        UserLog userLog = new UserLog();
        userLog.setUserId(rs.getString("S_USER_ID"));
        userLog.setAction(rs.getString("S_ACTION"));
        userLog.setCreateDate(rs.getTimestamp("D_CREATE"));
        return userLog;
    }

}
