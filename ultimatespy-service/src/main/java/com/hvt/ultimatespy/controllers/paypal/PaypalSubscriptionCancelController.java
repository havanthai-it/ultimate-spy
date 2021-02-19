package com.hvt.ultimatespy.controllers.paypal;

import com.hvt.ultimatespy.services.paypal.PaypalClient;
import com.hvt.ultimatespy.services.user.UserSubscriptionService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_PAYPAL_SUBSCRIPTION_ID)
public class PaypalSubscriptionCancelController {
    @Autowired
    private PaypalClient paypalClient;

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) throws Exception {
        if (id == null || id.isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        String accessToken = paypalClient.getAccessToken().get();

        Integer result0 = paypalClient.cancelSubscription(accessToken, id, "").get();
        if (result0 == 0) {
            throw Errors.INTERNAL_SERVER_ERROR;
        }

        int result1 = userSubscriptionService.update(id, "canceled").get();
        if (result1 == 0) throw Errors.INTERNAL_SERVER_ERROR;

        return ResponseEntity.ok(1);
    }
}
