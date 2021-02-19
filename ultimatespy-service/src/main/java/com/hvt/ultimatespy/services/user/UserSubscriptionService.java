package com.hvt.ultimatespy.services.user;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.user.UserSubscription;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
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
                    // "       AND d_from <= CURRENT_TIMESTAMP() " +
                    "       AND (s_status = 'approved' OR s_status = 'canceled') " +
                    " ORDER BY d_to, n_id DESC ";
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
                    " tb_user_subscription(S_USER_ID, S_PLAN_ID, D_FROM, D_TO, S_DESC, S_STATUS, S_PAYPAL_SUBSCRIPTION_ID, S_PAYPAL_PLAN_ID) " +
                    " VALUES(?,?,?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userSubscription.getUserId());
                cs.setString(2, userSubscription.getPlanId());
                cs.setTimestamp(3, new Timestamp(sdf.parse(userSubscription.getFrom()).getTime()));
                cs.setTimestamp(4, new Timestamp(sdf.parse(userSubscription.getTo()).getTime()));
                cs.setString(5, userSubscription.getDesc() != null ? userSubscription.getDesc() : "");
                cs.setString(6, userSubscription.getStatus());
                cs.setString(7, userSubscription.getPaypalSubscriptionId());
                cs.setString(8, userSubscription.getPaypalPlanId());
                cs.execute();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                return 0;
            } finally {
                Datasource.close(conn, cs, null);
            }
            return 1;
        });
    }

    public CompletableFuture<Integer> update(String paypalSubscriptionId, String status) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = " UPDATE tb_user_subscription SET " +
                    " s_status = ? " +
                    " WHERE s_paypal_subscription_id = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, status);
                cs.setString(2, paypalSubscriptionId);
                cs.execute();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                return 0;
            } finally {
                Datasource.close(conn, cs, null);
            }
            return 1;
        });
    }

    private static UserSubscription bindUserSubscription(ResultSet rs) throws SQLException {
        Timestamp tsFrom = rs.getTimestamp("D_FROM");
        Timestamp tsTo = rs.getTimestamp("D_TO");

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setId(rs.getInt("N_ID"));
        userSubscription.setPlanId(rs.getString("S_PLAN_ID"));
        userSubscription.setUserId(rs.getString("S_USER_ID"));
        userSubscription.setFrom(sdf.format(tsFrom));
        userSubscription.setTo(sdf.format(tsTo));
        userSubscription.setStatus(rs.getString("S_STATUS"));
        userSubscription.setPaypalSubscriptionId(rs.getString("S_PAYPAL_SUBSCRIPTION_ID"));
        userSubscription.setPaypalPlanId(rs.getString("S_PAYPAL_PLAN_ID"));
        return userSubscription;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

}
