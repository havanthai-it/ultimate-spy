package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.FuncUtils;
import com.hvt.ultimatespy.utils.mail.MailUtils;
import com.hvt.ultimatespy.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_FORGOT_PASSWORD)
public class UserForgotPasswordPostController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@PathVariable("mail") String mail) throws Exception {
        if (mail == null || mail.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User user = userService.getByEmail(mail).get();
        if (user != null) {
            // Send mail
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/mail/mail-reset-password.html");
            String mailContent = FuncUtils.readFromInputStream(is);
            mailContent = mailContent.replaceAll("\\$\\{USER_NAME\\}", user.getFirstName() + " " + user.getLastName());
            mailContent = mailContent.replaceAll("\\$\\{RESET_ID\\}", Base64.getEncoder().encodeToString(user.getEmail().getBytes()));
            MailUtils.send(mail, "Reset Your Password", mailContent);
        }

        return ResponseEntity.ok(null);
    }

}
