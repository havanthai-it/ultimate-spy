package com.hvt.ultimatespy.services.post;

import com.google.gson.Gson;
import com.hvt.ultimatespy.config.Config;
import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostQuery;
import com.hvt.ultimatespy.models.post.FacebookPostStatistic;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class FacebookPostService {

    private static final Logger logger = Logger.getLogger(FacebookPostService.class.getName());
    private static final String ES_HOST = Config.prop.getProperty("elasticsearch.host");
    private static final int ES_TIMEOUT = Integer.parseInt(Config.prop.getProperty("elasticsearch.timeout"));
    private static final Gson gson = new Gson();

    private final RestHighLevelClient client;

    public FacebookPostService() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(ES_HOST).build();
        client = RestClients.create(clientConfiguration).rest();
    }

    public CompletableFuture<FacebookPost> get (String facebookPostId) {
        return CompletableFuture.supplyAsync(() -> {
            FacebookPost facebookPost = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "{ CALL facebook_post_get(?) }";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, facebookPostId);

                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    facebookPost = bind(rs);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return facebookPost;
        });
    }

    public CompletableFuture<BaseList<FacebookPost>> list (FacebookPostQuery facebookPostQuery) {
        return CompletableFuture.supplyAsync(() -> {
            BaseList<FacebookPost> baseList = new BaseList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "{ CALL facebook_post_search(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setTimestamp(1, facebookPostQuery.getFromDate());
                cs.setTimestamp(2, facebookPostQuery.getToDate());
                cs.setInt(3, facebookPostQuery.getPage());
                cs.setInt(4, facebookPostQuery.getPageSize());
                cs.setString(5, facebookPostQuery.getKeyword());
                cs.setString(6, facebookPostQuery.getPixelId());
                cs.setString(7, facebookPostQuery.getFacebookPageUsername());
                cs.setString(8, facebookPostQuery.getCategory());
                cs.setString(9, facebookPostQuery.getType());
                cs.setString(10, facebookPostQuery.getCountry());
                cs.setString(11, facebookPostQuery.getLanguage());
                cs.setString(12, facebookPostQuery.getWebsite());
                cs.setString(13, facebookPostQuery.getPlatform());
                cs.setInt(14, facebookPostQuery.getMinLikes());
                cs.setInt(15, facebookPostQuery.getMaxLikes());
                cs.setInt(16, facebookPostQuery.getMinComments());
                cs.setInt(17, facebookPostQuery.getMaxComments());

                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    baseList.add(bind(rs));
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

    public CompletableFuture<BaseList<FacebookPost>> listUserPost (String userId, String userPostType, FacebookPostQuery facebookPostQuery) {
        return CompletableFuture.supplyAsync(() -> {
            BaseList<FacebookPost> baseList = new BaseList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "{ CALL user_post_search(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setTimestamp(1, facebookPostQuery.getFromDate());
                cs.setTimestamp(2, facebookPostQuery.getToDate());
                cs.setInt(3, facebookPostQuery.getPage());
                cs.setInt(4, facebookPostQuery.getPageSize());
                cs.setString(5, facebookPostQuery.getKeyword());
                cs.setString(6, facebookPostQuery.getPixelId());
                cs.setString(7, facebookPostQuery.getFacebookPageUsername());
                cs.setString(8, facebookPostQuery.getCategory());
                cs.setString(9, facebookPostQuery.getType());
                cs.setString(10, facebookPostQuery.getCountry());
                cs.setString(11, facebookPostQuery.getLanguage());
                cs.setString(12, facebookPostQuery.getWebsite());
                cs.setString(13, facebookPostQuery.getPlatform());
                cs.setInt(14, facebookPostQuery.getMinLikes());
                cs.setInt(15, facebookPostQuery.getMaxLikes());
                cs.setInt(16, facebookPostQuery.getMinComments());
                cs.setInt(17, facebookPostQuery.getMaxComments());
                cs.setString(18, userId);
                cs.setString(19, userPostType);

                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    baseList.add(bind(rs));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            long total = 0L;
            try {
                total = totalUserPost(userId, userPostType, facebookPostQuery).get();
                logger.info("[FacebookPostService.listUserPost] total=" + total);
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
            String sql = "{ CALL facebook_post_total(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.BIGINT);
                cs.setTimestamp(2, facebookPostQuery.getFromDate());
                cs.setTimestamp(3, facebookPostQuery.getToDate());
                cs.setString(4, facebookPostQuery.getKeyword());
                cs.setString(5, facebookPostQuery.getPixelId());
                cs.setString(6, facebookPostQuery.getFacebookPageUsername());
                cs.setString(7, facebookPostQuery.getCategory());
                cs.setString(8, facebookPostQuery.getType());
                cs.setString(9, facebookPostQuery.getCountry());
                cs.setString(10, facebookPostQuery.getLanguage());
                cs.setString(11, facebookPostQuery.getWebsite());
                cs.setString(12, facebookPostQuery.getPlatform());
                cs.setInt(13, facebookPostQuery.getMinLikes());
                cs.setInt(14, facebookPostQuery.getMaxLikes());
                cs.setInt(15, facebookPostQuery.getMinComments());
                cs.setInt(16, facebookPostQuery.getMaxComments());
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

    public BaseList<FacebookPost> search(FacebookPostQuery query) throws IOException {
        BaseList<FacebookPost> baseList = new BaseList<>();

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("s_content", query.getKeyword()).operator(Operator.AND));
        }
        if (query.getPixelId() != null && !query.getPixelId().trim().isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("s_pixel_id.keyword", query.getPixelId()));
        }
        if (query.getFacebookPageUsername() != null && !query.getFacebookPageUsername().trim().isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("s_facebook_page_username.keyword", query.getFacebookPageUsername()));
        }
        if (query.getCategory() != null && !query.getCategory().trim().isEmpty()) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            for (String category : query.getCategory().split(",")) {
                boolQuery.should(QueryBuilders.termQuery("s_category.keyword", category));
            }
            boolQuery.minimumShouldMatch(1);
            boolQueryBuilder.filter(boolQuery);
        }
        if (query.getType() != null && !query.getType().trim().isEmpty()) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            for (String type : query.getType().split(",")) {
                boolQuery.should(QueryBuilders.termQuery("s_type.keyword", type));
            }
            boolQuery.minimumShouldMatch(1);
            boolQueryBuilder.filter(boolQuery);
        }
        if (query.getWebsite() != null && !query.getWebsite().trim().isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("s_website.keyword", query.getWebsite()));
        }
        if (query.getPlatform() != null && !query.getPlatform().trim().isEmpty()) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            for (String platform : query.getPlatform().split(",")) {
                boolQuery.should(QueryBuilders.termQuery("s_platform.keyword", platform));
            }
            boolQuery.minimumShouldMatch(1);
            boolQueryBuilder.filter(boolQuery);
        }
        if (query.getFromDate() != null) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("d_publish").gte(new java.util.Date(query.getFromDate().getTime())));
        }
        if (query.getToDate() != null) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("d_publish").lte(new java.util.Date(query.getToDate().getTime())));
        }
        if (query.getMinLikes() != null && query.getMinLikes() > 0) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("n_likes").gte(query.getMinLikes()));
        }
        if (query.getMaxLikes() != null && query.getMaxLikes() < 10000) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("n_likes").lte(query.getMaxLikes()));
        }
        if (query.getMinComments() != null && query.getMinComments() > 0) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("n_comments").gte(query.getMinComments()));
        }
        if (query.getMaxComments() != null && query.getMaxComments() < 10000) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("n_comments").lte(query.getMaxComments()));
        }

        builder.query(boolQueryBuilder);
        builder.timeout(new TimeValue(ES_TIMEOUT, TimeUnit.MILLISECONDS));
        builder.sort(new FieldSortBuilder("d_publish").order(SortOrder.DESC));
        if (query.getPage() != null && query.getPageSize() != null) {
            builder.from(query.getPage() * query.getPageSize());
            builder.size(query.getPageSize());
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("es_facebook_post");
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = response.getHits().getHits();
        List<FacebookPost> list =
                Arrays.stream(searchHits)
                        .map(hit -> gson.fromJson(hit.getSourceAsString(), FacebookPost.class))
                        .collect(Collectors.toList());
        baseList.setList(list);
        baseList.setTotal(response.getHits().getTotalHits().value);
        return baseList;
    }

    public CompletableFuture<Long> totalUserPost(String userId, String userPostType, FacebookPostQuery facebookPostQuery) {
        return CompletableFuture.supplyAsync(() -> {
            long total = 0L;
            Connection conn = null;
            CallableStatement cs = null;
            String sql = "{ CALL user_post_total(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.BIGINT);
                cs.setTimestamp(2, facebookPostQuery.getFromDate());
                cs.setTimestamp(3, facebookPostQuery.getToDate());
                cs.setString(4, facebookPostQuery.getKeyword());
                cs.setString(5, facebookPostQuery.getPixelId());
                cs.setString(6, facebookPostQuery.getFacebookPageUsername());
                cs.setString(7, facebookPostQuery.getCategory());
                cs.setString(8, facebookPostQuery.getType());
                cs.setString(9, facebookPostQuery.getCountry());
                cs.setString(10, facebookPostQuery.getLanguage());
                cs.setString(11, facebookPostQuery.getWebsite());
                cs.setString(12, facebookPostQuery.getPlatform());
                cs.setInt(13, facebookPostQuery.getMinLikes());
                cs.setInt(14, facebookPostQuery.getMaxLikes());
                cs.setInt(15, facebookPostQuery.getMinComments());
                cs.setInt(16, facebookPostQuery.getMaxComments());
                cs.setString(17, userId);
                cs.setString(18, userPostType);
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

    public CompletableFuture<List<FacebookPostStatistic>> getStatistic (String facebookPostId) {
        return CompletableFuture.supplyAsync(() -> {
            List<FacebookPostStatistic> list = new ArrayList<>();
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = " SELECT * FROM tb_facebook_post_statistic " +
                    " WHERE s_facebook_post_id = ? ORDER BY d_date ASC ";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, facebookPostId);

                rs = cs.executeQuery();
                while (rs != null && rs.next()) {
                    FacebookPostStatistic postStatistic = new FacebookPostStatistic();
                    postStatistic.setFacebookPostId(rs.getString("S_FACEBOOK_POST_ID"));
                    postStatistic.setLikes(rs.getObject("N_LIKES") != null ? rs.getLong("N_LIKES") : 0);
                    postStatistic.setComments(rs.getObject("N_COMMENTS") != null ? rs.getLong("N_COMMENTS") : 0);
                    postStatistic.setShares(rs.getObject("N_SHARES") != null ? rs.getLong("N_SHARES") : 0);
                    postStatistic.setViews(rs.getObject("N_VIEWS") != null ? rs.getLong("N_VIEWS") : 0);
                    postStatistic.setDate(rs.getTimestamp("D_DATE"));
                    list.add(postStatistic);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return list;
        });
    }

    public static FacebookPost bind(ResultSet rs) throws SQLException {
        FacebookPost facebookPost = new FacebookPost();
        facebookPost.setId(rs.getString("S_ID"));
        facebookPost.setPostId(rs.getString("S_POST_ID"));
        facebookPost.setAdsId(rs.getString("S_ADS_ID"));
        facebookPost.setPixelId(rs.getString("S_PIXEL_ID"));
        facebookPost.setFacebookPageUsername(rs.getString("S_FACEBOOK_PAGE_USERNAME"));
        facebookPost.setImages(rs.getString("S_IMAGES"));
        facebookPost.setVideos(rs.getString("S_VIDEOS"));
        facebookPost.setContent(rs.getString("S_CONTENT"));
        facebookPost.setType(rs.getString("S_TYPE"));
        facebookPost.setCategory(rs.getString("S_CATEGORY"));
        facebookPost.setCountry(rs.getString("S_COUNTRY"));
        facebookPost.setLanguage(rs.getString("S_LANGUAGE"));
        facebookPost.setLikes(rs.getObject("N_LIKES") != null ? rs.getLong("N_LIKES") : 0);
        facebookPost.setComments(rs.getObject("N_COMMENTS") != null ? rs.getLong("N_COMMENTS") : 0);
        facebookPost.setShares(rs.getObject("N_SHARES") != null ? rs.getLong("N_SHARES") : 0);
        facebookPost.setViews(rs.getObject("N_VIEWS") != null ? rs.getLong("N_VIEWS") : 0);
        facebookPost.setStatus(rs.getString("S_STATUS"));
        facebookPost.setLinks(rs.getString("S_LINKS"));
        facebookPost.setWebsite(rs.getString("S_WEBSITE"));
        facebookPost.setPlatform(rs.getString("S_PLATFORM"));
        facebookPost.setPublishDate(rs.getTimestamp("D_PUBLISH"));
        facebookPost.setCreateDate(rs.getTimestamp("D_CREATE"));
        facebookPost.setUpdateDate(rs.getTimestamp("D_UPDATE"));

        // Page information
        facebookPost.setPageName(rs.getString("S_PAGE_NAME"));
        facebookPost.setPageUsername(rs.getString("S_PAGE_USERNAME"));
        facebookPost.setPageThumbnail(rs.getString("S_PAGE_THUMBNAIL"));
        facebookPost.setPageLikes(rs.getObject("N_PAGE_LIKES") != null ? rs.getLong("N_PAGE_LIKES") : 0);
        facebookPost.setPageFollows(rs.getObject("N_PAGE_FOLLOWS") != null ? rs.getLong("N_PAGE_FOLLOWS") : 0);
        facebookPost.setPagePublishDate(rs.getTimestamp("D_PAGE_PUBLISH"));

        return facebookPost;
    }

}
