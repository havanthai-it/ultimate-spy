package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserGetController {

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

}
