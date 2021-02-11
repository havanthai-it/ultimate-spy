package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.services.user.UserLimitationService;
import com.hvt.ultimatespy.services.user.UserLogService;
import com.hvt.ultimatespy.services.user.UserPostService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.enums.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_ID_POST)
public class UserPostPostController {

    @Autowired
    private UserPostService userPostService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserLimitationService userLimitationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@PathVariable String id, @RequestParam String facebookPostId, @RequestParam String type) throws Exception {
        if (id == null || id.trim().isEmpty()
            || facebookPostId == null || facebookPostId.trim().isEmpty()
            || type == null || type.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        Integer result = 0;
        if (type.toLowerCase().equals("saved")) {
            userLimitationService.checkLimitation(id, ActionEnum.SAVE_POST.value(), 24);
            result = userPostService.insert(id, facebookPostId, type, 0).get();
            userLogService.insert(id, ActionEnum.SAVE_POST.value());
        } else if (type.toLowerCase().equals("tracked")) {
            userLimitationService.checkLimitation(id, ActionEnum.TRACK_POST.value(), 24);
            result = userPostService.insert(id, facebookPostId, type, 1).get();
            userLogService.insert(id, ActionEnum.TRACK_POST.value());
        }

        return ResponseEntity.ok(result);
    }



}
