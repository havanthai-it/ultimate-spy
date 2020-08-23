package com.hvt.ultimatespy.controllers.post;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostQuery;
import com.hvt.ultimatespy.services.post.FacebookPostService;
import com.hvt.ultimatespy.utils.Constants;
import org.bouncycastle.util.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/post/facebook")
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
        String category = params.containsKey(Constants.CATEGORY) ? params.get(Constants.CATEGORY).trim() : Constants.BLANK;
        String type = params.containsKey(Constants.TYPE) ? params.get(Constants.TYPE).trim() : Constants.BLANK;
        String country = params.containsKey(Constants.COUNTRY) ? params.get(Constants.COUNTRY).trim() : Constants.BLANK;
        String language = params.containsKey(Constants.LANGUAGE) ? params.get(Constants.LANGUAGE).trim() : Constants.BLANK;
        String ecomSoftware = params.containsKey(Constants.ECOM_SOFTWARE) ? params.get(Constants.ECOM_SOFTWARE).trim() : Constants.BLANK;
        String ecomPlatform = params.containsKey(Constants.ECOM_PLATFORM) ? params.get(Constants.ECOM_PLATFORM).trim() : Constants.BLANK;

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
                "category=" + category + ", " +
                "type=" + type + ", " +
                "country=" + country + ", " +
                "language=" + language + ", " +
                "ecomSoftware=" + ecomSoftware + ", " +
                "ecomPlatform=" + ecomPlatform
        );

        FacebookPostQuery facebookPostQuery = new FacebookPostQuery(
                fromDate,
                toDate,
                page,
                pageSize,
                keyword,
                category,
                type,
                country,
                language,
                ecomSoftware,
                ecomPlatform);
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
