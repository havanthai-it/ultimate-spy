package com.hvt.ultimatespy.controllers.payment;

import com.hvt.ultimatespy.models.payment.Payment;
import com.hvt.ultimatespy.services.payment.PaymentService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_PAYMENT_ID)
public class PaymentPatchController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Integer> get(@PathVariable String id, @RequestBody Payment payment) throws Exception {
        if (id == null || id.isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (payment.getStatus() == null || payment.getStatus().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;

        int result = paymentService.update(id, payment.getStatus(), payment.getPaypalSubscriptionId(), payment.getPaypalPlanId()).get();
        return ResponseEntity.ok(result);
    }
}
