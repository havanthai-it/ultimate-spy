package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.enums.RoleEnum;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_USER)
public class UserPostController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> post(@RequestBody User user) throws Exception {
        if (user.getFirstName() == null || user.getFirstName().isEmpty()
            || user.getLastName() == null || user.getLastName().isEmpty()
            || user.getEmail() == null || user.getEmail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        if (userService.getByEmail(user.getEmail()).get() != null) {
            throw Errors.EMAIL_EXIST_EXCEPTION;
        }

        user.setId(Constants.USER_ID_PREFIX + UUID.randomUUID().toString().replaceAll("-", ""));
        user.setRole(RoleEnum.NORMAL.value());

        // Password was base64 encoded on the client side before send to server
        // Server continue encrypt with BCryptEncoder
        String rawPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes()));
        String encryptedPassword = UserUtils.encryptPassword(rawPassword);
        user.setPassword(encryptedPassword);

        User result = userService.insert(user).get();
        result.setPassword(null);
        return ResponseEntity.ok(result);
    }

}
