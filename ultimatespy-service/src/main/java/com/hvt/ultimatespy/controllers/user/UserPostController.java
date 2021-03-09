package com.hvt.ultimatespy.controllers.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hvt.ultimatespy.config.Config;
import com.hvt.ultimatespy.models.referral.Referral;
import com.hvt.ultimatespy.services.referral.ReferralService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.FuncUtils;
import com.hvt.ultimatespy.utils.enums.RoleEnum;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.mail.MailUtils;
import com.hvt.ultimatespy.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_USER)
public class UserPostController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> post(@RequestHeader("X-Ref-Code") String refCode, @RequestBody User user) throws Exception {

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
        String encryptedPassword = Encryptor.encrypt(rawPassword);
        user.setPassword(encryptedPassword);

        user.setStatus("created");

        // Referral
        if (refCode != null && !refCode.isEmpty()) {
            User referrer = referralService.getReferrerInfoByCode(refCode).get();
            if (referrer != null) {
                user.setReferrerId(referrer.getId());
            }
        }

        User result = userService.insert(user).get();
        result.setPassword(null);

        if (result.getReferrerId() != null && !result.getReferrerId().isEmpty()
            && refCode != null && !refCode.isEmpty()) {

            Referral referral = new Referral();
            referral.setReferrerId(result.getReferrerId());
            referral.setReferrerCode(refCode);
            referral.setUserId(result.getId());
            referral.setAction("signup");
            referralService.insert(referral);
        }

        // Send mail
        InputStream is = getClass().getClassLoader().getResourceAsStream("templates/mail/mail-sign-up.html");
        String mailContent = FuncUtils.readFromInputStream(is);
        mailContent = mailContent.replaceAll("\\$\\{USER_NAME\\}", result.getFirstName() + " " + result.getLastName());
        mailContent = mailContent.replaceAll("\\$\\{USER_ID\\}", result.getId());
        mailContent = mailContent.replaceAll("\\$\\{CONFIRM_ID\\}", Base64.getEncoder().encodeToString(result.getId().getBytes()));
        MailUtils.send(result.getEmail(), "Welcome to AdsCrawlr!", mailContent);

        return ResponseEntity.ok(result);
    }

}
