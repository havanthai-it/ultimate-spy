package com.hvt.ultimatespy.services.promotion;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.payment.Payment;
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
public class PromotionService {

    private static final Logger logger = Logger.getLogger(PromotionService.class.getName());

    public CompletableFuture<Integer> getDiscountPercent(String code) {
        return CompletableFuture.supplyAsync(() -> {
            Integer result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = " SELECT * FROM tb_promotion_code " +
                    " WHERE s_code = ? AND s_status = 'active' ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, code);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    result = rs.getInt("N_DISCOUNT_PERCENT");
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return result;
        });
    }

}
