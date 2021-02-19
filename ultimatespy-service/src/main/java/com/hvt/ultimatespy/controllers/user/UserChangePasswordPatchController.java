package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.models.user.UserChangePassword;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_CHANGE_PASSWORD)
public class UserChangePasswordPatchController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable String id, @RequestBody UserChangePassword userChangePassword) throws Exception {
        if (userChangePassword == null
            || id == null || id.isEmpty()
            || userChangePassword.getUserId() == null || userChangePassword.getUserId().isEmpty()
            || userChangePassword.getOldPassword() == null || userChangePassword.getOldPassword().isEmpty()
            || userChangePassword.getNewPassword() == null || userChangePassword.getNewPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User user = userService.get(userChangePassword.getUserId()).get();
        if (user != null) {
            String rawOldPassword = new String(Base64.getDecoder().decode(userChangePassword.getOldPassword().getBytes()));
            String rawNewPassword = new String(Base64.getDecoder().decode(userChangePassword.getNewPassword().getBytes()));
            String encryptedNewPassword = Encryptor.encrypt(rawNewPassword);
            if (Encryptor.verify(rawOldPassword, user.getPassword())) {
                user.setPassword(encryptedNewPassword);
            }
           userService.update(user).get();
        } else {
            throw Errors.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.ok(null);
    }

}
