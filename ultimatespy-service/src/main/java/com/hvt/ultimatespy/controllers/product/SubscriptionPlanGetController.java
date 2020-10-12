package com.hvt.ultimatespy.controllers.product;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.product.Subscription;
import com.hvt.ultimatespy.services.product.SubscriptionService;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_PRODUCT_SUBSCRIPTION_PLAN)
public class SubscriptionPlanGetController {

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(method = RequestMethod.GET)

    public ResponseEntity<BaseList<Subscription>> get() throws Exception {
        BaseList<Subscription> baseList = subscriptionPlanService.list().get();
        return ResponseEntity.ok(baseList);
    }

}
