package com.hvt.ultimatespy.services.referral;
import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.models.referral.ReferralRequestPayout;
import com.hvt.ultimatespy.models.referral.ReferralSummary;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ReferralRequestPayoutService {

    private static final Logger logger = Logger.getLogger(ReferralRequestPayoutService.class.getName());

    public CompletableFuture<List<ReferralRequestPayout>> list(String referrerId, String status) {
        return CompletableFuture.supplyAsync(() -> {
            List<ReferralRequestPayout> list = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT r.* " +
                    " FROM tb_referral_request_payout r " +
                    " WHERE r.s_referrer_id = ? AND r.s_status = ? " +
                    " ORDER BY r.d_create DESC ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referrerId);
                cs.setString(2, status);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    ReferralRequestPayout referralRequestPayout = new ReferralRequestPayout();
                    referralRequestPayout.setId(rs.getInt("N_ID"));
                    referralRequestPayout.setReferrerId(rs.getString("S_REFERRER_ID"));
                    referralRequestPayout.setStatus(rs.getString("S_STATUS"));
                    referralRequestPayout.setDateCreate(rs.getTimestamp("D_CREATE"));
                    list.add(referralRequestPayout);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return list;
        });
    }

    public CompletableFuture<Integer> total(String referrerId, String status) {
        return CompletableFuture.supplyAsync(() -> {
            Integer total = 0;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT count(*) " +
                    " FROM tb_referral_request_payout r " +
                    " WHERE r.s_referrer_id = ? AND r.s_status = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referrerId);
                cs.setString(2, status);
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

    public CompletableFuture<Integer> insert(String referrerId) {
        return CompletableFuture.supplyAsync(() -> {
            Integer result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "INSERT INTO tb_referral_request_payout ( "
                    + " s_referrer_id "
                    + " ) VALUES (?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, referrerId);
                cs.execute();
                result = 1;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }

            return result;
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

}
