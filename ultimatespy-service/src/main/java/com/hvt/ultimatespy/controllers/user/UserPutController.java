package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_USER)
public class UserPutController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> post(@RequestBody User user) throws Exception {
        if (user.getFullName() == null || user.getFullName().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User result = userService.update(user).get();
        result.setPassword(null);
        return ResponseEntity.ok(result);
    }

}
