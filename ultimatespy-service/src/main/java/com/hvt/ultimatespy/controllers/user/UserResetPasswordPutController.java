package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.FuncUtils;
import com.hvt.ultimatespy.utils.mail.MailUtils;
import com.hvt.ultimatespy.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.util.Base64;

@RestController
@RequestMapping(value = Constants.ROUTE_RESET_PASSWORD)
public class UserResetPasswordPutController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable("mail") String mail) throws Exception {
        if (mail == null || mail.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User user = userService.getByEmail(mail).get();
        if (user != null) {
            String newPassword = FuncUtils.randomString(8, true, true, true);
            String encryptedPassword = UserUtils.encryptPassword(newPassword);

            MailUtils.send(mail, "Reset Password", "encryptedPassword = " + encryptedPassword);
        }

        return ResponseEntity.ok(null);
    }

}
