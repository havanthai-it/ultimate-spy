package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.UserSubscription;
import com.hvt.ultimatespy.services.user.UserSubscriptionService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_SUBSCRIPTION)
public class UserSubscriptionPostController {

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserSubscription> post(@RequestBody UserSubscription userSubscription) throws Exception {
        if (userSubscription.getUserId() == null || userSubscription.getUserId().isEmpty()
            || userSubscription.getPlanId() == null || userSubscription.getPlanId().isEmpty()
                || userSubscription.getFrom() == null || userSubscription.getFrom().isEmpty()
                || userSubscription.getTo() == null || userSubscription.getTo().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        Integer result = userSubscriptionService.insert(userSubscription).get();
        if (result == 0) {
            throw Errors.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.ok(userSubscription);
    }

}
