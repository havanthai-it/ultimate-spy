package com.hvt.ultimatespy.services.payment;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.payment.Payment;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaymentService {

    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    public CompletableFuture<Payment> get(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Payment payment = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM tb_payment WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, id);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    payment = bindPayment(rs);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return payment;
        });
    }

    public CompletableFuture<Payment> insert(Payment payment) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "INSERT INTO tb_payment ( "
                    + " s_user_id, "
                    + " s_product_id, "
                    + " ) VALUES (?,?)"
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                cs.setString(2, "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return payment;
        });
    }

    public CompletableFuture<Payment> update(Payment payment) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "INSERT INTO tb_payment ( "
                    + " s_user_id, "
                    + " s_product_id, "
                    + " ) VALUES (?,?)"
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                cs.setString(2, "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return payment;
        });
    }

    public CompletableFuture<Payment> updateStatus(String id, String status) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "INSERT INTO tb_payment ( "
                    + " s_user_id, "
                    + " s_product_id, "
                    + " ) VALUES (?,?)"
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                cs.setString(2, "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return null;
        });
    }

    private static Payment bindPayment(ResultSet rs) {
        Payment payment = new Payment();

        return payment;
    }

}
