package com.hvt.ultimatespy.controllers.subscriberEmail;

import com.hvt.ultimatespy.models.subscriberEmail.SubscriberEmail;
import com.hvt.ultimatespy.services.subscriberEmail.SubscriberEmailService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = Constants.ROUTE_SUBSCRIBER_EMAIL)
public class SubscriberEmailPostController {

    @Autowired
    private SubscriberEmailService subscriberEmailService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Integer> post(@RequestBody SubscriberEmail subscriberEmail) throws Exception {
        if (subscriberEmail.getEmail() == null || subscriberEmail.getEmail().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        SubscriberEmail found = subscriberEmailService.get(subscriberEmail.getEmail()).get();

        if (found == null) {
            subscriberEmailService.insert(subscriberEmail.getEmail()).get();
        }
        return ResponseEntity.ok(1);
    }

}
