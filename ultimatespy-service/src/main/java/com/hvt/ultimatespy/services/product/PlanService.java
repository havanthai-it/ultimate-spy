package com.hvt.ultimatespy.services.product;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.product.Plan;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PlanService {

    private static final Logger logger = Logger.getLogger(PlanService.class.getName());

    public CompletableFuture<Plan> get(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Plan plan = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM tb_plan WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, id);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    plan = bindPlan(rs);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return plan;
        });
    }

    private static Plan bindPlan(ResultSet rs) {
        Plan plan = new Plan();

        return plan;
    }

}
