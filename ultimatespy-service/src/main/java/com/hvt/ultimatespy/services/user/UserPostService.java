package com.hvt.ultimatespy.services.user;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.services.post.FacebookPostService;
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
public class UserPostService {

    private static final Logger logger = Logger.getLogger(UserPostService.class.getName());

    public CompletableFuture<Integer> insert(String userId, String facebookPostId, String type) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "INSERT INTO " +
                    " tb_user_post(S_USER_ID, S_FACEBOOK_POST_ID, S_TYPE) " +
                    " VALUES(?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, facebookPostId);
                cs.setString(3, type);
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
                return 0;
            } finally {
                Datasource.close(conn, cs, null);
            }
            return 1;
        });
    }

    public CompletableFuture<List<String>> listIds(String userId, String type) {
        return CompletableFuture.supplyAsync(() -> {
            List<String> list = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT s_facebook_post_id " +
                    " FROM tb_user_post " +
                    " WHERE s_user_id = ? AND s_type = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, type);
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    list.add(rs.getString("S_FACEBOOK_POST_ID"));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return list;
        });
    }

    public CompletableFuture<Integer> delete(String userId, String facebookPostId, String type) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "DELETE " +
                    " FROM tb_user_post " +
                    " WHERE s_user_id = ? AND s_facebook_post_id =? AND s_type = ? ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, userId);
                cs.setString(2, facebookPostId);
                cs.setString(3, type);
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
                return 0;
            } finally {
                Datasource.close(conn, cs, null);
            }
            return 1;
        });
    }
}
