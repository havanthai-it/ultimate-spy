package com.hvt.ultimatespy.controllers.post;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.services.post.FacebookPostService;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/post/facebook")
public class FacebookPostGetController {

    private Logger logger = Logger.getLogger(FacebookPostGetController.class.getName());

    @Autowired
    private FacebookPostService facebookPostService;

    public ResponseEntity<Object> get(@RequestParam Map<String, String> params) {

        String keyword = params.containsKey(Constants.KEYWORD) ? params.get(Constants.KEYWORD).trim() : Constants.BLANK;
        String category = params.containsKey(Constants.CATEGORY) ? params.get(Constants.CATEGORY).trim() : Constants.BLANK;
        String creative = params.containsKey(Constants.CREATIVE) ? params.get(Constants.CREATIVE).trim() : Constants.BLANK;
        String country = params.containsKey(Constants.COUNTRY) ? params.get(Constants.COUNTRY).trim() : Constants.BLANK;
        String language = params.containsKey(Constants.LANGUAGE) ? params.get(Constants.LANGUAGE).trim() : Constants.BLANK;
        String ecomSoftware = params.containsKey(Constants.ECOM_SOFTWARE) ? params.get(Constants.ECOM_SOFTWARE).trim() : Constants.BLANK;
        String ecomWebsite = params.containsKey(Constants.ECOM_WEBSITE) ? params.get(Constants.ECOM_WEBSITE).trim() : Constants.BLANK;
        String fromDate = params.containsKey(Constants.FROM_DATE) ? params.get(Constants.FROM_DATE).trim() : Constants.BLANK;
        String toDate = params.containsKey(Constants.TO_DATE) ? params.get(Constants.TO_DATE).trim() : Constants.BLANK;

        logger.info("Search params: " +
                "keyword=" + keyword + ", " +
                "category=" + category + ", " +
                "creative=" + creative + ", " +
                "country=" + country + ", " +
                "language=" + language + ", " +
                "ecomSoftware=" + ecomSoftware + ", " +
                "ecomWebsite=" + ecomWebsite + ", " +
                "fromDate=" + fromDate + ", " +
                "toDate=" + toDate
        );

        BaseList<FacebookPost> baseList = new BaseList<>();
        try {
            baseList = facebookPostService.list(keyword, category, creative, country, language, ecomSoftware, ecomWebsite, fromDate, toDate).get();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

        return ResponseEntity.ok(baseList);
    }

}
