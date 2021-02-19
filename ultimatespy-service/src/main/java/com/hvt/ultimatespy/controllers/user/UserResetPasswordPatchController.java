package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.models.user.UserChangePassword;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Encryptor;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_RESET_PASSWORD)
public class UserResetPasswordPatchController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@RequestBody User user) throws Exception {
        if (user == null
            || user.getEmail() == null || user.getEmail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User foundUser = userService.getByEmail(user.getEmail()).get();
        if (foundUser != null) {
            String rawPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes()));
            String encryptedPassword = Encryptor.encrypt(rawPassword);
            foundUser.setPassword(encryptedPassword);
            userService.update(foundUser).get();
        } else {
            throw Errors.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.ok(null);
    }

}
