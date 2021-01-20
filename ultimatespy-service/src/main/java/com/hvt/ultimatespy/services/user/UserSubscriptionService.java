package com.hvt.ultimatespy.services.user;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.models.user.UserSubscription;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserSubscriptionService {

    private static final Logger logger = Logger.getLogger(UserSubscriptionService.class.getName());

    public CompletableFuture<List<UserSubscription>> list(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            List<UserSubscription> list = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * " +
                    " FROM tb_user_subscription " +
                    " WHERE s_user_id = ? " +
                    "       AND d_from <= CURRENT_TIMESTAMP() " +
                    "       AND d_to >= CURRENT_TIMESTAMP() " +
                    "       AND s_status = 'approved' " +
                    "ORDER BY n_id DESC";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    list.add(bindUserSubscription(rs));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return list;
        });
    }

    public CompletableFuture<Integer> insert(UserSubscription userSubscription) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "INSERT INTO " +
                    " tb_user_subscription(S_USER_ID, S_PLAN_ID, D_FROM, D_TO, S_DESC, S_STATUS) " +
                    " VALUES(?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userSubscription.getUserId());
                cs.setString(2, userSubscription.getPlanId());
                cs.setTimestamp(3, userSubscription.getFrom());
                cs.setTimestamp(4, userSubscription.getTo());
                cs.setString(5, userSubscription.getDesc() != null ? userSubscription.getDesc() : "");
                cs.setString(6, userSubscription.getStatus());
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

    private static UserSubscription bindUserSubscription(ResultSet rs) throws SQLException {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setId(rs.getInt("N_ID"));
        userSubscription.setPlanId(rs.getString("S_PLAN_ID"));
        userSubscription.setUserId(rs.getString("S_USER_ID"));
        userSubscription.setFrom(rs.getTimestamp("D_FROM"));
        userSubscription.setTo(rs.getTimestamp("D_TO"));
        userSubscription.setStatus(rs.getString("S_STATUS"));
        return userSubscription;
    }

}
