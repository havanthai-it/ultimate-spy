package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.ROUTE_USER)
public class UserGetController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> get(@RequestParam String id, @RequestParam String email) throws Exception {
        User user = null;

        if (id != null && !id.trim().isEmpty()) {
            user = userService.get(id).get();
        } else if (email != null & !email.trim().isEmpty()) {
            user = userService.getByEmail(email).get();
        } else {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

}
