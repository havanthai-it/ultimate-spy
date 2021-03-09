package com.hvt.ultimatespy.controllers.referral;

import com.hvt.ultimatespy.models.referral.ReferralRequestPayout;
import com.hvt.ultimatespy.services.referral.ReferralRequestPayoutService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRAL_REQUEST_PAYOUT_REFERRER_ID)
public class ReferralRequestPayoutGetController {
    @Autowired
    private ReferralRequestPayoutService referralRequestPayoutService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ReferralRequestPayout> > get(@PathVariable String referrerId) throws Exception {
        if (referrerId == null || referrerId.isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;

        List<ReferralRequestPayout> result = referralRequestPayoutService.list(referrerId, "pending").get();
        return ResponseEntity.ok(result);
    }
}
