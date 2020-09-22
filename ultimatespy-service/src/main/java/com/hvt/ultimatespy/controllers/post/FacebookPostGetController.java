package com.hvt.ultimatespy.controllers.post;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostQuery;
import com.hvt.ultimatespy.services.post.FacebookPostService;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = Constants.ROUTE_POST_FACEBOOK)
public class FacebookPostGetController {

    private static final Logger logger = Logger.getLogger(FacebookPostGetController.class.getName());

    @Autowired
    private FacebookPostService facebookPostService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> get(@RequestParam Map<String, String> params) throws Exception {

        Timestamp fromDate = params.containsKey(Constants.FROM_DATE) && !params.get(Constants.FROM_DATE).trim().isEmpty() ? new Timestamp(sdf.parse(params.get(Constants.FROM_DATE).trim()).getTime()) : null;
        Timestamp toDate = params.containsKey(Constants.TO_DATE) && !params.get(Constants.TO_DATE).trim().isEmpty() ? new Timestamp(sdf.parse(params.get(Constants.TO_DATE).trim()).getTime()) : null;
        int page = params.containsKey(Constants.PAGE) ? Integer.parseInt(params.get(Constants.PAGE).trim()) : 0;
        int pageSize = params.containsKey(Constants.PAGE_SIZE) ? Integer.parseInt(params.get(Constants.PAGE_SIZE).trim()) : 30;
        String keyword = params.containsKey(Constants.KEYWORD) ? params.get(Constants.KEYWORD).trim() : Constants.BLANK;
        String pixelId = params.containsKey(Constants.PIXEL_ID) ? params.get(Constants.PIXEL_ID).trim() : Constants.BLANK;
        String facebookPageId = params.containsKey(Constants.FACEBOOK_PAGE_ID) ? params.get(Constants.FACEBOOK_PAGE_ID).trim() : Constants.BLANK;
        String category = params.containsKey(Constants.CATEGORY) ? params.get(Constants.CATEGORY).trim() : Constants.BLANK;
        String type = params.containsKey(Constants.TYPE) ? params.get(Constants.TYPE).trim() : Constants.BLANK;
        String country = params.containsKey(Constants.COUNTRY) ? params.get(Constants.COUNTRY).trim() : Constants.BLANK;
        String language = params.containsKey(Constants.LANGUAGE) ? params.get(Constants.LANGUAGE).trim() : Constants.BLANK;
        String website = params.containsKey(Constants.WEBSITE) ? params.get(Constants.WEBSITE).trim() : Constants.BLANK;
        String platform = params.containsKey(Constants.PLATFORM) ? params.get(Constants.PLATFORM).trim() : Constants.BLANK;
        long minLikes = params.containsKey(Constants.MIN_LIKES) && !params.get(Constants.MIN_LIKES).trim().isEmpty() ? Long.parseLong(params.get(Constants.MIN_LIKES)) : 0;
        long maxLikes = params.containsKey(Constants.MAX_LIKES) && !params.get(Constants.MAX_LIKES).trim().isEmpty() ? Long.parseLong(params.get(Constants.MAX_LIKES)) : 999999999999L;

        // Add '+' before every word in keyword
        keyword = keyword.replaceAll(" +", " +");
        keyword = keyword.isEmpty() ? Constants.BLANK : "+" + keyword;

        // Set default fromDate, toDate
        if (toDate == null) {
            toDate = Timestamp.from(Instant.now());
        }
        if (fromDate == null) {
            fromDate = Timestamp.from(toDate.toInstant().minusSeconds(365*24*60*60));
        }

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
                "maxLikes=" + maxLikes
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
                maxLikes);
        BaseList<FacebookPost> baseList = new BaseList<>();
        try {
            baseList = facebookPostService.list(facebookPostQuery).get();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

        return ResponseEntity.ok(baseList);
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

}
