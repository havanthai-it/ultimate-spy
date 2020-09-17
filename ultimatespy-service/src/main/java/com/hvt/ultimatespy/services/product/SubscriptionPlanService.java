package com.hvt.ultimatespy.services.product;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.product.SubscriptionPlan;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
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
public class SubscriptionPlanService {

    private static final Logger logger = Logger.getLogger(SubscriptionPlanService.class.getName());

    public CompletableFuture<BaseList<SubscriptionPlan>> list() {
        return CompletableFuture.supplyAsync(() -> {
            BaseList<SubscriptionPlan> baseList = new BaseList<>();
            List<SubscriptionPlan> list = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM tb_subscription_plan";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    list.add(bindSubscriptionPlan(rs));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            baseList.setTotal(list.size());
            return baseList;
        });
    }

    private static SubscriptionPlan bindSubscriptionPlan(ResultSet rs) {
        SubscriptionPlan sp = new SubscriptionPlan();

        return sp;
    }

}
