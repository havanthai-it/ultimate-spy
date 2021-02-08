package com.hvt.ultimatespy.controllers.payment;

import com.hvt.ultimatespy.models.payment.Payment;
import com.hvt.ultimatespy.services.payment.PaymentService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = Constants.ROUTE_PAYMENT)
public class PaymentListController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> list(@RequestParam String userId) throws Exception {
        if (userId == null || userId.isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }
        List<Payment> payments = paymentService.getByUser(userId).get();
        return ResponseEntity.ok(payments);
    }
}
