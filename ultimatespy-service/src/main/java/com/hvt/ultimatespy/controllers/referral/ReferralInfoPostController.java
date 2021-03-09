package com.hvt.ultimatespy.controllers.referral;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.referral.ReferralService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.FuncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRER)
public class ReferralInfoPostController {

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> post(@RequestBody User user) throws Exception {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }
        String code;
        User tempUser = null;
        do {
            code = FuncUtils.randomString(8, false, true, true);
            tempUser = referralService.getReferrerInfoByCode(code).get();
        } while (tempUser != null);

        user.setCode(code);
        User result = referralService.insertReferrerInfo(user.getId(), user.getCode(), user.getPaypalName(), user.getPaypalAccount()).get();
        result.setPassword(null);
        return ResponseEntity.ok(result);
    }

}
