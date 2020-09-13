package com.hvt.ultimatespy.services.post;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostQuery;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacebookPostService {

    private static final Logger logger = Logger.getLogger(FacebookPostService.class.getName());

    public CompletableFuture<BaseList<FacebookPost>> list (FacebookPostQuery facebookPostQuery) {
        return CompletableFuture.supplyAsync(() -> {
            BaseList<FacebookPost> baseList = new BaseList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "CALL search_facebook_post(?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setTimestamp(1, facebookPostQuery.getFromDate());
                cs.setTimestamp(2, facebookPostQuery.getToDate());
                cs.setInt(3, facebookPostQuery.getPage());
                cs.setInt(4, facebookPostQuery.getPageSize());
                cs.setString(5, facebookPostQuery.getPixelId());
                cs.setString(6, facebookPostQuery.getKeyword());
                cs.setString(7, facebookPostQuery.getCategory());
                cs.setString(8, facebookPostQuery.getType());
                cs.setString(9, facebookPostQuery.getCountry());
                cs.setString(10, facebookPostQuery.getLanguage());
                cs.setString(11, facebookPostQuery.getWebsite());
                cs.setString(12, facebookPostQuery.getPlatform());
                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    FacebookPost facebookPost = new FacebookPost();
                    facebookPost.setId(rs.getString("S_ID"));
                    facebookPost.setPostId(rs.getString("S_POST_ID"));
                    facebookPost.setAdsId(rs.getString("S_ADS_ID"));
                    facebookPost.setPixelId(rs.getString("S_PIXEL_ID"));
                    facebookPost.setFacebookPageId(rs.getString("S_FACEBOOK_PAGE_ID"));
                    facebookPost.setImages(rs.getString("S_IMAGES"));
                    facebookPost.setVideos(rs.getString("S_VIDEOS"));
                    facebookPost.setContent(rs.getString("S_CONTENT"));
                    facebookPost.setType(rs.getString("S_TYPE"));
                    facebookPost.setCategory(rs.getString("S_CATEGORY"));
                    facebookPost.setCountry(rs.getString("S_COUNTRY"));
                    facebookPost.setLanguage(rs.getString("S_LANGUAGE"));
                    facebookPost.setLikes(rs.getObject("N_LIKES") != null ? rs.getInt("N_LIKES") : 0);
                    facebookPost.setComments(rs.getObject("N_COMMENTS") != null ? rs.getInt("N_COMMENTS") : 0);facebookPost.setLikes(rs.getObject("S_LIKES") != null ? rs.getInt("S_LIKES") : 0);
                    facebookPost.setShares(rs.getObject("N_SHARES") != null ? rs.getInt("N_SHARES") : 0);
                    facebookPost.setViews(rs.getObject("N_VIEWS") != null ? rs.getInt("N_VIEWS") : 0);
                    facebookPost.setStatus(rs.getString("S_STATUS"));
                    facebookPost.setLinks(rs.getString("S_LINKS"));
                    facebookPost.setWebsite(rs.getString("S_WEBSITE"));
                    facebookPost.setPlatform(rs.getString("S_PLATFORM"));
                    facebookPost.setAdsFromDate(rs.getTimestamp("D_ADS_FROM"));
                    facebookPost.setAdsToDate(rs.getTimestamp("D_ADS_TO"));
                    facebookPost.setPublishDate(rs.getTimestamp("D_PUBLISH"));
                    facebookPost.setCreateDate(rs.getTimestamp("D_CREATE"));
                    facebookPost.setUpdateDate(rs.getTimestamp("D_UPDATE"));
                    baseList.add(facebookPost);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            long total = 0L;
            try {
                total = total(facebookPostQuery).get();
                logger.info("[FacebookPostService.list] total=" + total);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            }
            baseList.setTotal(total);
            return baseList;
        });
    }

    public CompletableFuture<Long> total(FacebookPostQuery facebookPostQuery) {
        return CompletableFuture.supplyAsync(() -> {
            long total = 0L;
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "CALL total_facebook_post(?,?,?,?,?,?,?,?,?,?,?)";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.BIGINT);
                cs.setTimestamp(2, facebookPostQuery.getFromDate());
                cs.setTimestamp(3, facebookPostQuery.getToDate());
                cs.setString(4, facebookPostQuery.getPixelId());
                cs.setString(5, facebookPostQuery.getKeyword());
                cs.setString(6, facebookPostQuery.getCategory());
                cs.setString(7, facebookPostQuery.getType());
                cs.setString(8, facebookPostQuery.getCountry());
                cs.setString(9, facebookPostQuery.getLanguage());
                cs.setString(10, facebookPostQuery.getWebsite());
                cs.setString(11, facebookPostQuery.getPlatform());
                cs.execute();

                total = cs.getLong(1);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }
            return total;
        });
    }

}
