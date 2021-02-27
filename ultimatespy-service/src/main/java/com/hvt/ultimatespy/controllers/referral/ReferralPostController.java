package com.hvt.ultimatespy.controllers.referral;

import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.services.referral.ReferralService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRAL)
public class ReferralPostController {
    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Referral> post(@RequestBody Referral referral) throws Exception {
        if (referral.getReferrerId() == null || referral.getReferrerId().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (referral.getUserId() == null || referral.getUserId().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (referral.getAction() == null || referral.getAction().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;

        Referral result = referralService.insert(referral).get();
        return ResponseEntity.ok(result);
    }
}
