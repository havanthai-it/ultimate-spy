package com.hvt.ultimatespy.controllers.referral;

import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.models.referral.ReferralSummary;
import com.hvt.ultimatespy.services.referral.ReferralService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRAL)
public class ReferralSummaryGetController {

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ReferralSummary> list(@RequestParam String userId) throws Exception {
        if (userId == null || userId.isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        Integer totalClick = referralService.total(userId, "click").get();
        Integer totalSignup = referralService.total(userId, "signup").get();
        Integer totalPay = referralService.total(userId, "pay").get();
        List<Referral> payReferrals = referralService.list(userId, "pay").get();
        ReferralSummary referralSummary = new ReferralSummary(totalClick, totalSignup, totalPay, payReferrals);
        return ResponseEntity.ok(referralSummary);
    }
}
