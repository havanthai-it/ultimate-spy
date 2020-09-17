package com.hvt.ultimatespy.controllers.payment;

import com.hvt.ultimatespy.models.payment.Payment;
import com.hvt.ultimatespy.services.payment.PaymentService;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_PAYMENT_ID)
public class PaymentGetController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Payment> get(@PathVariable String id) throws Exception {
        Payment payment = paymentService.get(id).get();
        return ResponseEntity.ok(payment);
    }
}
