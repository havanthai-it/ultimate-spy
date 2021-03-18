package com.hvt.ultimatespy.controllers.promotion;

import com.hvt.ultimatespy.services.promotion.PromotionService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_PROMOTION_CODE)
public class PromotionGetController {
    @Autowired
    private PromotionService promotionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable String code) throws Exception {
        if (code == null || code.isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        Integer discountPercent = promotionService.getDiscountPercent(code).get();
        return ResponseEntity.ok(discountPercent);
    }
}
