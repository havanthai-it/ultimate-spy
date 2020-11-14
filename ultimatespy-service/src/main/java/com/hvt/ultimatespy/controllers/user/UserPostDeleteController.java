package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.services.user.UserPostService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_ID_POST)
public class UserPostDeleteController {

    @Autowired
    private UserPostService userPostService;

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> get(@PathVariable String id, @RequestParam String facebookPostId, @RequestParam String type) throws Exception {
        if (id == null || id.trim().isEmpty()
            || facebookPostId == null || facebookPostId.trim().isEmpty()
            || type == null || type.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        Integer result = 0;
        if (type.toLowerCase().equals("saved")) {
            result = userPostService.delete(id, facebookPostId, type).get();
        } else if (type.toLowerCase().equals("tracked")) {
            result = userPostService.delete(id, facebookPostId, type).get();
        }

        return ResponseEntity.ok(result);
    }



}
