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
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> get(@RequestParam String id) {
        User user = new User();
        user.setEmail("havanthai.it@gmail.com");
        user.setFullName("Ha Van Thai");
        user.setRole("ADMIN");
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> signup(@RequestBody User user) throws Exception {
        if (user.getFullName() == null || user.getFullName().isEmpty()
            || user.getEmail() == null || user.getEmail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setRole(RoleEnum.FREE.getRole());

        User result = userService.insert(user);
        return ResponseEntity.ok(result);
    }

}
