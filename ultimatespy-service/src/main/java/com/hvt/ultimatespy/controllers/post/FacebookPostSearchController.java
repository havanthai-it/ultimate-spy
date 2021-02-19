package com.hvt.ultimatespy.controllers.post;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostQuery;
import com.hvt.ultimatespy.models.post.FacebookPostStatistic;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.post.FacebookPostService;
import com.hvt.ultimatespy.services.user.UserLimitationService;
import com.hvt.ultimatespy.services.user.UserLogService;
import com.hvt.ultimatespy.services.user.UserPostService;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.enums.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = Constants.ROUTE_POST_FACEBOOK_SEARCH)
public class FacebookPostSearchController {

    private static final Logger logger = Logger.getLogger(FacebookPostSearchController.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private FacebookPostService facebookPostService;

    @Autowired
    private UserPostService userPostService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserLimitationService userLimitationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(Constants.X_USER_ID) String userId, @RequestParam Map<String, String> params) throws Exception {

        Timestamp fromDate = params.containsKey(Constants.FROM_DATE) && !params.get(Constants.FROM_DATE).trim().isEmpty() ? new Timestamp(sdf.parse(params.get(Constants.FROM_DATE).trim()).getTime()) : null;
        Timestamp toDate = params.containsKey(Constants.TO_DATE) && !params.get(Constants.TO_DATE).trim().isEmpty() ? new Timestamp(sdf.parse(params.get(Constants.TO_DATE).trim()).getTime()) : null;
        int page = params.containsKey(Constants.PAGE) ? Integer.parseInt(params.get(Constants.PAGE).trim()) : 0;
        int pageSize = params.containsKey(Constants.PAGE_SIZE) ? Integer.parseInt(params.get(Constants.PAGE_SIZE).trim()) : 24;
        String keyword = params.containsKey(Constants.KEYWORD) ? params.get(Constants.KEYWORD).trim() : Constants.BLANK;
        String pixelId = params.containsKey(Constants.PIXEL_ID) ? params.get(Constants.PIXEL_ID).trim() : Constants.BLANK;
        String facebookPageId = params.containsKey(Constants.FACEBOOK_PAGE_ID) ? params.get(Constants.FACEBOOK_PAGE_ID).trim() : Constants.BLANK;
        String category = params.containsKey(Constants.CATEGORY) ? params.get(Constants.CATEGORY).trim() : Constants.BLANK;
        String type = params.containsKey(Constants.TYPE) ? params.get(Constants.TYPE).trim() : Constants.BLANK;
        String country = params.containsKey(Constants.COUNTRY) ? params.get(Constants.COUNTRY).trim() : Constants.BLANK;
        String language = params.containsKey(Constants.LANGUAGE) ? params.get(Constants.LANGUAGE).trim() : Constants.BLANK;
        String website = params.containsKey(Constants.WEBSITE) ? params.get(Constants.WEBSITE).trim() : Constants.BLANK;
        String platform = params.containsKey(Constants.PLATFORM) ? params.get(Constants.PLATFORM).trim() : Constants.BLANK;
        int minLikes = params.containsKey(Constants.MIN_LIKES) && !params.get(Constants.MIN_LIKES).trim().isEmpty() ? Integer.parseInt(params.get(Constants.MIN_LIKES)) : 0;
        int maxLikes = params.containsKey(Constants.MAX_LIKES) && !params.get(Constants.MAX_LIKES).trim().isEmpty() ? Integer.parseInt(params.get(Constants.MAX_LIKES)) : Integer.MAX_VALUE;
        int minComments = params.containsKey(Constants.MIN_COMMENTS) && !params.get(Constants.MIN_COMMENTS).trim().isEmpty() ? Integer.parseInt(params.get(Constants.MIN_COMMENTS)) : 0;
        int maxComments = params.containsKey(Constants.MAX_COMMENTS) && !params.get(Constants.MAX_COMMENTS).trim().isEmpty() ? Integer.parseInt(params.get(Constants.MAX_COMMENTS)) : Integer.MAX_VALUE;

        logger.info("Search params: " +
                "fromDate=" + (fromDate != null ? sdf.format(fromDate) : "") + ", " +
                "toDate=" + (fromDate != null ? sdf.format(toDate) : "") + ", " +
                "page=" + page + ", " +
                "pageSize=" + pageSize + ", " +
                "keyword=" + keyword + ", " +
                "pixelId=" + pixelId + ", " +
                "facebookPageId=" + facebookPageId + ", " +
                "category=" + category + ", " +
                "type=" + type + ", " +
                "country=" + country + ", " +
                "language=" + language + ", " +
                "website=" + website + ", " +
                "platform=" + platform + ", " +
                "minLikes=" + minLikes + ", " +
                "maxLikes=" + maxLikes + ", " +
                "minComments=" + minComments + ", " +
                "maxComments=" + maxComments
        );

        if (platform.contains("pod")) {
            platform = platform.replace("pod", "etsy,gearbubble,redbubble,spreadshirt,sunfrog,teehag,teespring,teepublic,teechip,teezily,teemill");
        }

        FacebookPostQuery facebookPostQuery = new FacebookPostQuery(
                fromDate,
                toDate,
                page,
                pageSize,
                keyword,
                pixelId,
                facebookPageId,
                category,
                type,
                country,
                language,
                website,
                platform,
                minLikes,
                maxLikes,
                minComments,
                maxComments);

        if (userId != null && !facebookPostQuery.isEmpty()) {
            userLimitationService.checkLimitation(userId, ActionEnum.SEARCH.value(), 24);
        }

        // When not signed in, can only search with default params
        if ((userId == null || userId.isEmpty()) && !facebookPostQuery.isEmpty()) {
            throw Errors.USER_UNAUTHORIZED;
        }

        BaseList<FacebookPost> baseList = new BaseList<>();
        try {
            if (userId != null && !userId.trim().isEmpty() && keyword.toLowerCase().startsWith("::saved")) {
                baseList = facebookPostService.listUserPost(userId, "saved", facebookPostQuery).get();
            } else if (userId != null && !userId.trim().isEmpty() && keyword.toLowerCase().startsWith("::tracked")) {
                baseList = facebookPostService.listUserPost(userId, "tracked", facebookPostQuery).get();
            } else if (keyword.toLowerCase().startsWith("::pixel=")) {
                facebookPostQuery.setPixelId(keyword.substring(8).trim());
                facebookPostQuery.setKeyword(Constants.BLANK);
                baseList = facebookPostService.search(facebookPostQuery).get();
                if (userId != null && !facebookPostQuery.isEmpty()) {
                    userLogService.insert(userId, ActionEnum.SEARCH.value());
                }
            } else if (keyword.toLowerCase().startsWith("::website=")) {
                facebookPostQuery.setWebsite(keyword.substring(10).trim());
                facebookPostQuery.setKeyword(Constants.BLANK);
                baseList = facebookPostService.search(facebookPostQuery).get();
                if (userId != null && !facebookPostQuery.isEmpty()) {
                    userLogService.insert(userId, ActionEnum.SEARCH.value());
                }
            } else {
                baseList = facebookPostService.search(facebookPostQuery).get();
                if (userId != null && !facebookPostQuery.isEmpty()) {
                    userLogService.insert(userId, ActionEnum.SEARCH.value());
                }
            }

            if (userId != null && !userId.trim().isEmpty()) {
                List<String> listSavedIds = userPostService.listIds(userId, "saved").get();
                List<String> listTrackedIds = userPostService.listIds(userId, "tracked").get();
                baseList.getList().stream()
                        .filter(p -> listSavedIds.contains(p.getPostId()))
                        .forEach(p -> p.setSaved(true));
                baseList.getList().stream()
                        .filter(p -> listTrackedIds.contains(p.getPostId()))
                        .forEach(p -> {
                            p.setTracked(true);
                            List<FacebookPostStatistic> statistics = null;
                            try {
                                statistics = facebookPostService.getStatistic(p.getPostId()).get();
                                if (statistics.size() > 1) {
                                    FacebookPostStatistic stats1 = statistics.get(statistics.size() - 1);
                                    FacebookPostStatistic stats2 = statistics.get(statistics.size() - 2);
                                    float likesChange = stats1.getLikes() - stats2.getLikes();
                                    float commentsChange = stats1.getComments() - stats2.getComments();
                                    float shareChange = stats1.getShares() - stats2.getShares();
                                    p.setLastLikeTrack(stats2.getLikes() != 0 ? (likesChange / stats2.getLikes()) : likesChange);
                                    p.setLastCommentTrack(stats2.getComments() != 0 ? (commentsChange / stats2.getComments()) : commentsChange);
                                    p.setLastShareTrack(stats2.getShares() != 0 ? (shareChange / stats2.getShares()) : shareChange);
                                    p.setLastAvgTrack((p.getLastLikeTrack() + p.getLastCommentTrack() + p.getLastShareTrack()) / 3);
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                logger.log(Level.SEVERE, "", e);
                            }
                        });
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

        return ResponseEntity.ok(baseList);
    }

}
