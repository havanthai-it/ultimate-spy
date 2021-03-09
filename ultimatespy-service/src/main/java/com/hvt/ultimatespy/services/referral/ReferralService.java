package com.hvt.ultimatespy.services.referral;
import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.models.referral.ReferralSummary;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ReferralService {

    private static final Logger logger = Logger.getLogger(ReferralService.class.getName());

    @Autowired
    private UserService userService;

    public CompletableFuture<List<Referral>> list(String referrerId, String action) {
        return CompletableFuture.supplyAsync(() -> {
            List<Referral> referrals = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT r.*, u.s_email " +
                    " FROM tb_referral r " +
                    " INNER JOIN tb_user u ON u.s_id = r.s_user_id " +
                    " WHERE r.s_referrer_id = ? AND r.s_action = ? " +
                    " ORDER BY r.d_create DESC ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referrerId);
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

    public CompletableFuture<Integer> total(String referrerId, String action) {
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
                cs.setString(1, referrerId);
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
                    + " s_referrer_code, "
                    + " s_user_id, "
                    + " s_action, "
                    + " s_payment_id, "
                    + " n_payment_amount, "
                    + " n_commission_amount "
                    + " ) VALUES (?,?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referral.getReferrerId());
                cs.setString(2, referral.getReferrerCode());
                cs.setString(3, referral.getUserId() != null ? referral.getUserId() : "");
                cs.setString(4, referral.getAction());
                cs.setString(5, referral.getPaymentId() != null ? referral.getPaymentId() : "");
                cs.setDouble(6, referral.getPaymentAmount() != null ? referral.getPaymentAmount() : 0d);
                cs.setDouble(7, referral.getCommissionAmount() != null ? referral.getCommissionAmount() : 0d);
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return referral;
        });
    }

    public CompletableFuture<Integer> update(String referrerId, String referrerCode, String userId, String action, String settlementStatus) {
        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "UPDATE tb_referral SET "
                    + " s_settlement_status = ?, "
                    + " d_settlement = CURRENT_TIMESTAMP() "
                    + " WHERE s_referrer_id = ? AND s_referrer_code = ? AND s_user_id = ? AND s_action = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referrerId);
                cs.setString(2, referrerCode);
                cs.setString(3, userId);
                cs.setString(4, action);
                cs.setString(5, settlementStatus);
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

    public CompletableFuture<User> getReferrerInfo(String id) {
        return CompletableFuture.supplyAsync(() -> {
            User result = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = " SELECT * FROM tb_referral_info WHERE s_referrer_id = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, id);
                rs = cs.executeQuery();

                if (rs != null && rs.next()) {
                    result = new User();
                    result.setId(rs.getString("S_REFERRER_ID"));
                    result.setCode(rs.getString("S_REFERRER_CODE"));
                    result.setPaypalName(rs.getString("S_PAYPAL_NAME"));
                    result.setPaypalAccount(rs.getString("S_PAYPAL_ACCOUNT"));
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return result;
        });
    }

    public CompletableFuture<User> getReferrerInfoByCode(String code) {
        return CompletableFuture.supplyAsync(() -> {
            User result = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = " SELECT * FROM tb_referral_info WHERE s_referrer_code= ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, code);
                rs = cs.executeQuery();

                if (rs != null && rs.next()) {
                    result = new User();
                    result.setId(rs.getString("S_REFERRER_ID"));
                    result.setCode(rs.getString("S_REFERRER_CODE"));
                    result.setPaypalName(rs.getString("S_PAYPAL_NAME"));
                    result.setPaypalAccount(rs.getString("S_PAYPAL_ACCOUNT"));
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return result;
        });
    }

    public CompletableFuture<User> insertReferrerInfo(String id, String code, String paypalName, String paypalAccount) {
        return CompletableFuture.supplyAsync(() -> {
            User result = null;
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "INSERT INTO tb_referral_info ( " +
                    "   s_referrer_id, " +
                    "   s_referrer_code, " +
                    "   s_paypal_name, " +
                    "   s_paypal_account" +
                    " ) " +
                    " VALUES(?,?,?,?) ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, id);
                cs.setString(2, code);
                cs.setString(3, paypalName);
                cs.setString(4, paypalAccount);
                cs.execute();

                result = userService.get(id).get();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return result;
        });
    }

    public CompletableFuture<User> updateReferrerInfo(String id, String code, String paypalName, String paypalAccount) {
        return CompletableFuture.supplyAsync(() -> {
            User result = null;
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "UPDATE tb_referral_info SET " +
                    " s_referrer_code = ?, " +
                    " s_paypal_name = ?, " +
                    " s_paypal_account = ? " +
                    " WHERE s_referrer_id = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, code);
                cs.setString(2, paypalName);
                cs.setString(3, paypalAccount);
                cs.setString(4, id);
                cs.execute();

                result = userService.get(id).get();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return result;
        });
    }

    public CompletableFuture<ReferralSummary> summary(String referrerId) {
        return CompletableFuture.supplyAsync(() -> {
            ReferralSummary referralSummary = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "{ CALL referrer_summary(?,?,?,?,?,?) }";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.DOUBLE);
                cs.registerOutParameter(2, Types.DOUBLE);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.registerOutParameter(4, Types.INTEGER);
                cs.registerOutParameter(5, Types.INTEGER);
                cs.setString(6, referrerId);
                cs.execute();
                referralSummary = new ReferralSummary();
                referralSummary.setTotalRevenue(cs.getDouble(1));
                referralSummary.setTotalPaid(cs.getDouble(2));
                referralSummary.setTotalCustomers(cs.getInt(3));
                referralSummary.setTotalSignups(cs.getInt(4));
                referralSummary.setTotalClicks(cs.getInt(5));
                referralSummary.setTotalOwed(referralSummary.getTotalRevenue() - referralSummary.getTotalPaid());
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return referralSummary;
        });
    }

    private static Referral bindReferral(ResultSet rs) throws SQLException {
        Referral referral = new Referral();
        referral.setReferrerId(rs.getString("S_REFERRER_ID"));
        referral.setReferrerCode(rs.getString("S_REFERRER_CODE"));
        referral.setUserId(rs.getString("S_USER_ID"));
        referral.setEmail(rs.getString("S_EMAIL"));
        referral.setAction(rs.getString("S_ACTION"));
        referral.setPaymentId(rs.getString("S_PAYMENT_ID"));
        referral.setPaymentAmount(rs.getObject("N_PAYMENT_AMOUNT") != null ? rs.getDouble("N_PAYMENT_AMOUNT") : 0d);
        referral.setCommissionAmount(rs.getObject("N_COMMISSION_AMOUNT") != null ? rs.getDouble("N_COMMISSION_AMOUNT") : 0d);
        referral.setSettlementStatus(rs.getString("S_SETTLEMENT_STATUS"));
        referral.setDateCreate(rs.getObject("D_CREATE") != null ? rs.getTimestamp("D_CREATE") : null);
        referral.setDateSettlement(rs.getObject("D_SETTLEMENT") != null ? rs.getTimestamp("D_SETTLEMENT") : null);
        return referral;
    }

}
