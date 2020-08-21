package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.config.enums.RoleEnum;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserPostController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> post(@RequestBody User user) throws Exception {
        if (user.getFullName() == null || user.getFullName().isEmpty()
            || user.getEmail() == null || user.getEmail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setRole(RoleEnum.FREE.getRole());

        User result = userService.insert(user).get();
        return ResponseEntity.ok(result);
    }

}
