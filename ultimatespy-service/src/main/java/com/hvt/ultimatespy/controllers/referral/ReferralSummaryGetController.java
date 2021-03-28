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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = Constants.ROUTE_REFERRAL_SUMMARY)
public class ReferralSummaryGetController {

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ReferralSummary> list(@RequestParam String referrerId) throws Exception {
        if (referrerId == null || referrerId.isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        List<Referral> payReferrals = referralService.list(referrerId, "pay").get();
        for (Referral referral : payReferrals) {
            String mail = referral.getEmail();
            String[] arr = mail.split("@");
            String newMail = arr[0].charAt(0) + arr[0].substring(1).replaceAll("(.)", "*") + "@" + arr[1];
            referral.setEmail(newMail);
        }

        ReferralSummary referralSummary = referralService.summary(referrerId).get();
        referralSummary.setPayReferrals(payReferrals);
        return ResponseEntity.ok(referralSummary);
    }
}
