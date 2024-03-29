package com.hvt.ultimatespy.controllers.post;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostQuery;
import com.hvt.ultimatespy.models.user.UserLog;
import com.hvt.ultimatespy.services.post.FacebookPostService;
import com.hvt.ultimatespy.services.user.UserLimitationService;
import com.hvt.ultimatespy.services.user.UserLogService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.enums.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = Constants.ROUTE_POST_FACEBOOK)
public class FacebookPostListController {

    private static final Logger logger = Logger.getLogger(FacebookPostListController.class.getName());

    @Autowired
    private FacebookPostService facebookPostService;

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
        String filter = params.containsKey(Constants.FILTER) ? params.get(Constants.FILTER).trim() : Constants.BLANK;
        String sort = params.containsKey(Constants.SORT) ? params.get(Constants.SORT).trim() : Constants.BLANK;

        logger.info("Search params: " +
                "fromDate=" + sdf.format(fromDate) + ", " +
                "toDate=" + sdf.format(toDate) + ", " +
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
                "maxComments=" + maxComments + ", " +
                "filter=" + filter + ", " +
                "sort=" + sort
        );

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
                maxComments,
                filter,
                sort);

        if (userId != null && !facebookPostQuery.isEmpty()) {
            userLimitationService.checkLimitation(userId, ActionEnum.SEARCH.value(), 24);
        }

        // When not signed in, can only search with default params
        if ((userId == null || userId.isEmpty()) && !facebookPostQuery.isEmpty()) {
            throw Errors.USER_UNAUTHORIZED;
        }

        // Add '+' before every word in keyword
        String newKeyword = keyword.replaceAll(" +", " +");
        newKeyword = newKeyword.isEmpty() ? Constants.BLANK : "+" + newKeyword;

        // Set default fromDate, toDate
        if (toDate == null) {
            toDate = Timestamp.from(Instant.now());
        }
        if (fromDate == null) {
            fromDate = Timestamp.from(toDate.toInstant().minusSeconds(365 * 24 * 60 * 60));
        }

        facebookPostQuery.setKeyword(newKeyword);
        facebookPostQuery.setFromDate(fromDate);
        facebookPostQuery.setToDate(toDate);

        BaseList<FacebookPost> baseList = new BaseList<>();
        try {
            if (userId != null && !userId.trim().isEmpty() && filter.equalsIgnoreCase("saved")) {
                baseList = facebookPostService.listUserPost(userId, "saved", facebookPostQuery).get();
            } else if (userId != null && !userId.trim().isEmpty() && filter.equalsIgnoreCase("tracked")) {
                baseList = facebookPostService.listUserPost(userId, "tracked", facebookPostQuery).get();
            } else if (keyword.toLowerCase().startsWith("::pixel=")) {
                facebookPostQuery.setPixelId(keyword.substring(8).trim());
                facebookPostQuery.setKeyword(Constants.BLANK);
                baseList = facebookPostService.list(facebookPostQuery).get();
                if (userId != null && !facebookPostQuery.isEmpty()) {
                    userLogService.insert(userId, ActionEnum.SEARCH.value());
                }
            } else if (keyword.toLowerCase().startsWith("::website=")) {
                facebookPostQuery.setWebsite(keyword.substring(10).trim());
                facebookPostQuery.setKeyword(Constants.BLANK);
                baseList = facebookPostService.list(facebookPostQuery).get();
                if (userId != null && !facebookPostQuery.isEmpty()) {
                    userLogService.insert(userId, ActionEnum.SEARCH.value());
                }
            } else {
                baseList = facebookPostService.list(facebookPostQuery).get();
                if (userId != null && !facebookPostQuery.isEmpty()) {
                    userLogService.insert(userId, ActionEnum.SEARCH.value());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

        return ResponseEntity.ok(baseList);
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

}
