package com.hvt.ultimatespy.controllers.subscriptionPlan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hvt.ultimatespy.models.invoice.Invoice;
import com.hvt.ultimatespy.services.invoice.InvoiceService;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
