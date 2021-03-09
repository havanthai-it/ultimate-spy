package com.hvt.ultimatespy.controllers.referral;

import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.services.referral.ReferralRequestPayoutService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRAL_REQUEST_PAYOUT_REFERRER_ID)
public class ReferralRequestPayoutPostController {
    @Autowired
    private ReferralRequestPayoutService referralRequestPayoutService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Integer> post(@PathVariable String referrerId) throws Exception {
        if (referrerId == null || referrerId.isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;

        Integer result = referralRequestPayoutService.insert(referrerId).get();
        return ResponseEntity.ok(result);
    }
}
