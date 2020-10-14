package com.hvt.ultimatespy.services.user;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.models.user.UserSubscription;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    public CompletableFuture<User> get(String id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM tb_user WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, id);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    user = bindUser(rs);
                    List<UserSubscription> lstUserSubscription = userSubscriptionService.list(user.getId()).get();
                    user.setLstSubscriptions(lstUserSubscription);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return user;
        });
    }

    public CompletableFuture<User> getByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> {
            User user = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM tb_user WHERE s_email = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, email);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    user = bindUser(rs);
                    List<UserSubscription> lstUserSubscription = userSubscriptionService.list(user.getId()).get();
                    user.setLstSubscriptions(lstUserSubscription);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return user;
        });
    }

    public CompletableFuture<User> insert(User user) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = Datasource.getConnection();
            cs = conn.prepareCall("INSERT INTO " +
                    " tb_user(S_ID,S_FULL_NAME,S_EMAIL,S_PASSWORD,S_ROLE, S_REFERRER_ID) " +
                    " VALUES(?,?,?,?,?,?)");
            cs.setString(1, user.getId());
            cs.setString(2, user.getFullName());
            cs.setString(3, user.getEmail());
            cs.setString(4, user.getPassword());
            cs.setString(5, user.getRole());
            cs.setString(6, user.getReferrerId());
            cs.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);
        } finally {
            Datasource.close(conn, cs, null);
        }
        return getByEmail(user.getEmail());
    }

    public CompletableFuture<User> update(User user) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = Datasource.getConnection();
            cs = conn.prepareCall("UPDATE tb_user SET " +
                    " S_FULL_NAME = ? " +
                    " WHERE S_ID = ?");
            cs.setString(1, user.getFullName());
            cs.setString(2, user.getId());
            cs.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);
        } finally {
            Datasource.close(conn, cs, null);
        }
        return getByEmail(user.getEmail());
    }

    public CompletableFuture updateStatus(String id, String status) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "UPDATE tb_user SET s_status = ? WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, status);
                cs.setString(2, id);
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return null;
        });
    }

    private static User bindUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("S_ID"));
        user.setGoogleId(rs.getString("S_GOOGLE_ID"));
        user.setFullName(rs.getString("S_FULL_NAME"));
        user.setEmail(rs.getString("S_EMAIL"));
        user.setPassword(rs.getString("S_PASSWORD"));
        user.setRole(rs.getString("S_ROLE"));
        user.setStatus(rs.getString("S_STATUS"));
        user.setCreateDate(rs.getTimestamp("D_CREATE") != null ? sdf.format(rs.getTimestamp("D_CREATE")) : "");
        user.setUpdateDate(rs.getTimestamp("D_UPDATE") != null ? sdf.format(rs.getTimestamp("D_UPDATE")) : "");
        return user;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
}
