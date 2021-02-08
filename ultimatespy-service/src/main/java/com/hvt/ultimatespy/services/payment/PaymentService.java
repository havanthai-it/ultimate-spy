package com.hvt.ultimatespy.services.payment;

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

    public CompletableFuture<List<Payment>> getByUser(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Payment> payments = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT " +
                    " i.s_plan,p.s_invoice_id,p.s_id s_payment_id,p.s_user_id,p.s_currency,p.n_amount,p.d_create,p.s_status " +
                    " FROM tb_payment p " +
                    " INNER JOIN tb_invoice i ON i.s_id = p.s_invoice_id " +
                    " WHERE p.s_user_id = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    payments.add(bindPayment(rs));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return payments;
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

    private static Payment bindPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPlan(rs.getString("S_PLAN"));
        payment.setInvoiceId(rs.getString("S_INVOICE_ID"));
        payment.setId(rs.getString("S_PAYMENT_ID"));
        payment.setUserId(rs.getString("S_USER_ID"));
        payment.setCurrency(rs.getString("S_CURRENCY"));
        payment.setAmount(rs.getDouble("N_AMOUNT"));
        payment.setCreateDate(rs.getTimestamp("D_CREATE"));
        payment.setStatus(rs.getString("S_STATUS"));
        return payment;
    }

}
