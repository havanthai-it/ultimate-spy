package com.hvt.ultimatespy.controllers.post;

import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.models.post.FacebookPostStatistic;
import com.hvt.ultimatespy.services.post.FacebookPostService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.DateUtils;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = Constants.ROUTE_POST_FACEBOOK_ID)
public class FacebookPostGetController {

    private static final Logger logger = Logger.getLogger(FacebookPostGetController.class.getName());

    @Autowired
    private FacebookPostService facebookPostService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        logger.info("Get post by id = " + id);
        FacebookPost post = new FacebookPost();
        List<FacebookPostStatistic> statistics = new ArrayList<>();
        try {
            post = facebookPostService.get(id).get();
            statistics = facebookPostService.getStatistic(id).get();

            // Convert statistics to chart data
            post.setStatistics(toChartData(post, statistics));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

        return ResponseEntity.ok(post);
    }

    private static List<Map<String, Object>> toChartData(FacebookPost post, List<FacebookPostStatistic> statistics) {
        Timestamp minDate = post.getPublishDate();
        Timestamp maxDate = statistics.get(statistics.size() - 1).getDate();
        SimpleDateFormat sdf = DateUtils.compareDateOnly(minDate, maxDate) < 0 ? sdfDate : sdfTime;

        List<Map<String, Object>> data = new ArrayList<>();
        List<Map<String, Object>> seriesLike = new ArrayList<>();
        List<Map<String, Object>> seriesComment = new ArrayList<>();
        List<Map<String, Object>> seriesShare = new ArrayList<>();

        seriesLike.add(new HashMap<String, Object>(){{
            put(Constants.NAME, sdf.format(post.getPublishDate()));
            put(Constants.VALUE, 0);
        }});
        seriesComment.add(new HashMap<String, Object>(){{
            put(Constants.NAME, sdf.format(post.getPublishDate()));
            put(Constants.VALUE, 0);
        }});
        seriesShare.add(new HashMap<String, Object>(){{
            put(Constants.NAME, sdf.format(post.getPublishDate()));
            put(Constants.VALUE, 0);
        }});

        for (FacebookPostStatistic item : statistics) {
            Map<String, Object> foundLike = seriesLike.stream()
                    .filter(m -> m.get(Constants.NAME).equals(sdf.format(item.getDate())))
                    .findFirst().orElse(null);
            if (foundLike == null) {
                seriesLike.add(new HashMap<String, Object>(){{
                    put(Constants.NAME, sdf.format(item.getDate()));
                    put(Constants.VALUE, item.getLikes());
                }});
            } else {
                foundLike.put(Constants.VALUE, item.getLikes());
            }

            Map<String, Object> foundComment = seriesComment.stream()
                    .filter(m -> m.get(Constants.NAME).equals(sdf.format(item.getDate())))
                    .findFirst().orElse(null);
            if (foundComment == null) {
                seriesComment.add(new HashMap<String, Object>(){{
                    put(Constants.NAME, sdf.format(item.getDate()));
                    put(Constants.VALUE, item.getComments());
                }});
            } else {
                foundComment.put(Constants.VALUE, item.getComments());
            }

            Map<String, Object> foundShare = seriesShare.stream()
                    .filter(m -> m.get(Constants.NAME).equals(sdf.format(item.getDate())))
                    .findFirst().orElse(null);
            if (foundShare == null) {
                seriesShare.add(new HashMap<String, Object>(){{
                    put(Constants.NAME, sdf.format(item.getDate()));
                    put(Constants.VALUE, item.getShares());
                }});
            } else {
                foundShare.put(Constants.VALUE, item.getShares());
            }
        }

        Map<String, Object> mapLike = new HashMap<String, Object>(){{
            put(Constants.NAME, "Like");
            put(Constants.SERIES, seriesLike);
        }};
        Map<String, Object> mapComment = new HashMap<String, Object>(){{
            put(Constants.NAME, "Comment");
            put(Constants.SERIES, seriesComment);
        }};
        Map<String, Object> mapShare = new HashMap<String, Object>(){{
            put(Constants.NAME, "Share");
            put(Constants.SERIES, seriesShare);
        }};

        data.add(mapLike);
        data.add(mapComment);
        data.add(mapShare);
        return data;
    }

    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("MMM dd");
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

}
