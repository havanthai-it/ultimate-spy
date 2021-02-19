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
                    " s_id,s_user_id,s_payment_method,n_amount,n_fee,n_tax,n_discount,n_origin_amount,s_currency,d_create,d_update,s_status,s_plan_id,s_paypal_subscription_id,s_paypal_plan_id " +
                    " FROM tb_payment " +
                    " WHERE s_user_id = ? AND s_status <> 'pending' " +
                    " ORDER BY d_create DESC ";
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
            String sql = "INSERT INTO tb_payment ( "
                    + " s_id, "
                    + " s_user_id, "
                    + " s_payment_method, "
                    + " n_amount, "
                    + " n_fee, "
                    + " n_tax, "
                    + " n_discount, "
                    + " n_origin_amount, "
                    + " s_currency, "
                    + " s_status, "
                    + " s_plan_id "
                    + " ) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, payment.getId());
                cs.setString(2, payment.getUserId());
                cs.setString(3, payment.getPaymentMethod());
                cs.setDouble(4, payment.getAmount());
                cs.setDouble(5, payment.getFee());
                cs.setDouble(6, payment.getTax());
                cs.setDouble(7, payment.getDiscount());
                cs.setDouble(8, payment.getOriginAmount());
                cs.setString(9, payment.getCurrency());
                cs.setString(10, payment.getStatus());
                cs.setString(11, payment.getPlanId());
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return payment;
        });
    }

    public CompletableFuture<Integer> update(String id, String status, String paypalSubscriptionId, String paypalPlanId) {
        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "UPDATE tb_payment SET "
                    + " s_status = ?, "
                    + " s_paypal_subscription_id = ?, "
                    + " s_paypal_plan_id = ?, "
                    + " d_update = CURRENT_TIMESTAMP() "
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, status);
                cs.setString(2, paypalSubscriptionId);
                cs.setString(3, paypalPlanId);
                cs.setString(4, id);
                cs.execute();
                result = 1;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return result;
        });
    }

    public CompletableFuture<Integer> delete(String userId, String status) {
        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "DELETE FROM tb_payment "
                    + " WHERE s_user_id = ? AND s_status = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, status);
                cs.execute();
                result = 1;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return result;
        });
    }

    private static Payment bindPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getString("S_ID"));
        payment.setUserId(rs.getString("S_USER_ID"));
        payment.setPaymentMethod(rs.getString("S_PAYMENT_METHOD"));
        payment.setAmount(rs.getDouble("N_AMOUNT"));
        payment.setFee(rs.getDouble("N_FEE"));
        payment.setTax(rs.getDouble("N_TAX"));
        payment.setDiscount(rs.getDouble("N_DISCOUNT"));
        payment.setOriginAmount(rs.getDouble("N_ORIGIN_AMOUNT"));
        payment.setCurrency(rs.getString("S_CURRENCY"));
        payment.setCreateDate(rs.getTimestamp("D_CREATE"));
        payment.setUpdateDate(rs.getTimestamp("D_UPDATE"));
        payment.setStatus(rs.getString("S_STATUS"));
        payment.setPlanId(rs.getString("S_PLAN_ID"));
        payment.setPaypalSubscriptionId(rs.getString("S_PAYPAL_SUBSCRIPTION_ID"));
        payment.setPaypalPlanId(rs.getString("S_PAYPAL_PLAN_ID"));
        return payment;
    }

}
