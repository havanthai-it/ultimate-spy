package com.hvt.ultimatespy.services.referral;
import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.payment.Payment;
import com.hvt.ultimatespy.models.referral.Referral;
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
public class ReferralService {

    private static final Logger logger = Logger.getLogger(ReferralService.class.getName());

    public CompletableFuture<List<Referral>> list(String userId, String action) {
        return CompletableFuture.supplyAsync(() -> {
            List<Referral> referrals = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT r.*, u.s_email " +
                    " FROM tb_referral r " +
                    " INNER JOIN tb_user u ON u.s_id = r.s_user_id " +
                    " WHERE r.s_user_id = ? AND r.s_action = ? " +
                    " ORDER BY r.d_create DESC ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, action);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    referrals.add(bindReferral(rs));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return referrals;
        });
    }

    public CompletableFuture<Integer> total(String userId, String action) {
        return CompletableFuture.supplyAsync(() -> {
            Integer total = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT count(*) " +
                    " FROM tb_referral r " +
                    " WHERE r.s_user_id = ? AND r.s_action = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, action);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    total = rs.getInt(1);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return total;
        });
    }

    public CompletableFuture<Referral> insert(Referral referral) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "INSERT INTO tb_referral ( "
                    + " s_referrer_id, "
                    + " s_user_id, "
                    + " s_action, "
                    + " s_payment_id, "
                    + " n_payment_amount, "
                    + " n_commission_amount, "
                    + " s_ref_code "
                    + " ) VALUES (?,?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referral.getReferrerId());
                cs.setString(2, referral.getUserId());
                cs.setString(3, referral.getAction());
                cs.setString(4, referral.getPaymentId() != null ? referral.getPaymentId() : "");
                cs.setDouble(5, referral.getPaymentAmount() != null ? referral.getPaymentAmount() : 0d);
                cs.setDouble(6, referral.getCommissionAmount() != null ? referral.getCommissionAmount() : 0d);
                cs.setString(7, referral.getReferrerId() != null ? referral.getReferrerId() : "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return referral;
        });
    }

    public CompletableFuture<Integer> update(String referrerId, String userId, String action, String settlementStatus) {
        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "UPDATE tb_referral SET "
                    + " s_settlement_status = ?, "
                    + " d_settlement = CURRENT_TIMESTAMP() "
                    + " WHERE s_referrer_id = ? AND s_user_id = ? AND s_action = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referrerId);
                cs.setString(2, userId);
                cs.setString(3, action);
                cs.setString(4, settlementStatus);
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

    private static Referral bindReferral(ResultSet rs) throws SQLException {
        Referral referral = new Referral();
        referral.setReferrerId(rs.getString("S_REFERRER_ID"));
        referral.setUserId(rs.getString("S_USER_ID"));
        referral.setAction(rs.getString("S_ACTION"));
        referral.setPaymentId(rs.getString("S_PAYMENT_ID"));
        referral.setPaymentAmount(rs.getObject("N_PAYMENT_AMOUNT") != null ? rs.getDouble("N_PAYMENT_AMOUNT") : 0d);
        referral.setCommissionAmount(rs.getObject("N_COMMISSION_AMOUNT") != null ? rs.getDouble("N_COMMISSION_AMOUNT") : 0d);
        referral.setRefCode(rs.getString("S_REF_CODE"));
        referral.setSettlementStatus(rs.getString("S_SETTLEMENT_STATUS"));
        referral.setDateCreate(rs.getObject("D_CREATE") != null ? rs.getTimestamp("N_COMMISSION_AMOUNT") : null);
        referral.setDateSettlement(rs.getObject("D_SETTLEMENT") != null ? rs.getTimestamp("N_COMMISSION_AMOUNT") : null);
        return referral;
    }

}
