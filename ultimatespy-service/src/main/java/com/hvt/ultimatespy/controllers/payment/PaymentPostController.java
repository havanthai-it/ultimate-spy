package com.hvt.ultimatespy.controllers.payment;

import com.hvt.ultimatespy.config.Config;
import com.hvt.ultimatespy.models.payment.Payment;
import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.payment.PaymentService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_PAYMENT)
public class PaymentPostController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Payment> get(@RequestBody Payment payment) throws Exception {
        if (payment.getUserId() == null || payment.getUserId().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (payment.getAmount() == null || payment.getAmount() < 0) throw Errors.BAD_REQUEST_EXCEPTION;
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;

        paymentService.delete(payment.getUserId(), "pending").get();

        payment.setId(Constants.PAYMENT_ID_PREFIX + UUID.randomUUID().toString().replaceAll("-", ""));
        Payment result = paymentService.insert(payment).get();


        User user = userService.get(payment.getUserId()).get();
        if (user.getReferrerId() != null && !user.getReferrerId().isEmpty()) {
            Referral referral = new Referral();
            referral.setReferrerId(user.getReferrerId());
            referral.setReferrerCode("");
            referral.setUserId(user.getId());
            referral.setAction("pay");
            referral.setPaymentId(result.getId());
            referral.setPaymentAmount(result.getAmount());

            List<Payment> listPayments = paymentService.getByUser(user.getId()).get();
            int commission = Integer.parseInt(Config.prop.getProperty("referral.commission.firstTime"));
            if (listPayments.size() > 1) {
                commission = Integer.parseInt(Config.prop.getProperty("referral.commission.lifeTime"));
            }
            referral.setCommissionAmount(Math.round(commission * result.getAmount()) / 100d);
            referralService.insert(referral);
        }

        return ResponseEntity.ok(result);
    }
}
