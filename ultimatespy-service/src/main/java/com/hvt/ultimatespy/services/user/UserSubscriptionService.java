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
            String sql = "";
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

    private static UserSubscription bindUserSubscription(ResultSet rs) throws SQLException {
        UserSubscription userSubscription = new UserSubscription();
        return userSubscription;
    }

}
