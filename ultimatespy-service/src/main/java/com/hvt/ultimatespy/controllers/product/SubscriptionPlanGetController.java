package com.hvt.ultimatespy.controllers.product;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.product.SubscriptionPlan;
import com.hvt.ultimatespy.services.product.SubscriptionPlanService;
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
    private SubscriptionPlanService subscriptionPlanService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BaseList<SubscriptionPlan>> get() throws Exception {
        BaseList<SubscriptionPlan> baseList = subscriptionPlanService.list().get();
        return ResponseEntity.ok(baseList);
    }

}
