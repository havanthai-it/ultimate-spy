package com.hvt.ultimatespy.services.post;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacebookPostService {

    private static final Logger logger = Logger.getLogger(FacebookPostService.class.getName());

    public CompletableFuture<BaseList<FacebookPost>> list (
            String keyword,
            String category,
            String creative,
            String country,
            String language,
            String ecomSoftware,
            String ecomWebsite,
            String fromDate,
            String toDate
    ) {
        return CompletableFuture.supplyAsync(() -> {
            BaseList<FacebookPost> baseList = new BaseList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }

            long total = 0L;
            try {
                total = total(keyword, category, creative, country, language, ecomSoftware, ecomWebsite, fromDate, toDate).get();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            }
            baseList.setList(null);
            baseList.setTotal(total);
            return baseList;
        });
    }

    public CompletableFuture<Long> total(
            String keyword,
            String category,
            String creative,
            String country,
            String language,
            String ecomSoftware,
            String ecomWebsite,
            String fromDate,
            String toDate
    ) {
        return CompletableFuture.supplyAsync(() -> {
            long total = 0L;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }
            return total;
        });
    }

}
