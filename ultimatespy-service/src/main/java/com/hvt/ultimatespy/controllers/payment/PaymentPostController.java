package com.hvt.ultimatespy.controllers.payment;

import com.hvt.ultimatespy.models.payment.Payment;
import com.hvt.ultimatespy.services.payment.PaymentService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_PAYMENT)
public class PaymentPostController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Payment> get(@RequestBody Payment payment) throws Exception {
        if (payment.getUserId() == null || payment.getUserId().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (payment.getAmount() == null || payment.getAmount() < 0) throw Errors.BAD_REQUEST_EXCEPTION;
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;

        paymentService.delete(payment.getUserId(), "pending").get();

        payment.setId(Constants.PAYMENT_ID_PREFIX + UUID.randomUUID().toString().replaceAll("-", ""));
        Payment result = paymentService.insert(payment).get();
        return ResponseEntity.ok(result);
    }
}
