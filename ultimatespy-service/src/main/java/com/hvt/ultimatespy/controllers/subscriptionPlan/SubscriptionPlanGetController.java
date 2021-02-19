package com.hvt.ultimatespy.controllers.subscriptionPlan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = Constants.ROUTE_SUBSCRIPTION_PLAN)
public class SubscriptionPlanGetController {

    public final Gson gson = new Gson();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> get() throws Exception {
        File resource = new ClassPathResource("subscription-plan.json").getFile();
        String json = new String(Files.readAllBytes(resource.toPath()));
        Map<String, Object> map = new ObjectMapper().readValue(json, HashMap.class);
        return ResponseEntity.ok(map);
    }

}
