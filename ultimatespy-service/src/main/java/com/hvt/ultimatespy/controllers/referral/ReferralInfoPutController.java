package com.hvt.ultimatespy.controllers.referral;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.referral.ReferralService;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRER_ID)
public class ReferralInfoPutController {

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> put(@RequestBody User user) throws Exception {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User result = referralService.updateReferrerInfo(user.getId(), user.getCode(), user.getPaypalName(), user.getPaypalAccount()).get();
        result.setPassword(null);
        return ResponseEntity.ok(result);
    }

}
