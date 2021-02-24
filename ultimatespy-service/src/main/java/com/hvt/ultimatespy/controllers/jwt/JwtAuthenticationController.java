package com.hvt.ultimatespy.controllers.jwt;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hvt.ultimatespy.config.Config;
import com.hvt.ultimatespy.models.jwt.JwtRequest;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.jwt.JwtUserDetailsService;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Encryptor;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.enums.PlanEnum;
import com.hvt.ultimatespy.utils.enums.RoleEnum;
import com.hvt.ultimatespy.utils.enums.StatusEnum;
import com.hvt.ultimatespy.utils.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = Constants.ROUTE_AUTHENTICATE)
public class JwtAuthenticationController {
    private static final Logger logger = Logger.getLogger(JwtAuthenticationController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authorize(@RequestBody JwtRequest jwtRequest) throws Exception {

        if (jwtRequest.getType() == null) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User user = null;

        if (jwtRequest.getType().equals("standard")) {
            if (jwtRequest.getUsername() == null || jwtRequest.getPassword() == null) {
                throw Errors.BAD_REQUEST_EXCEPTION;
            }

            // Password was base64 encoded on the client side before send to server
            String rawPassword = new String(Base64.getDecoder().decode(jwtRequest.getPassword().getBytes()));
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), rawPassword)
                );
            } catch (DisabledException e) {
                throw new Exception("USER_DISABLED", e);
            } catch (BadCredentialsException e) {
                throw new Exception("INVALID_CREDENTIALS", e);
            }

            user = userService.getByEmail(jwtRequest.getUsername()).get();
            if (user.getStatus().equals(StatusEnum.CREATED.value())) {
                throw Errors.USER_NOT_CONFIRMED_EXCEPTION;
            } else if (user.getStatus().equals(StatusEnum.INACTIVE.value())) {
                throw Errors.USER_INACTIVE_EXCEPTION;
            }
        } else if (jwtRequest.getType().equals("google")) {
            if (jwtRequest.getGoogleIdToken() == null) {
                throw Errors.BAD_REQUEST_EXCEPTION;
            }

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(Config.prop.getProperty("google.clientId")))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            GoogleIdToken idToken = verifier.verify(jwtRequest.getGoogleIdToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                if (emailVerified) {
                    user = userService.getByEmail(payload.getEmail()).get();
                    if (user == null) {
                        user = new User();
                        user.setGoogleId(payload.getSubject());
                        user.setEmail(payload.getEmail());
                        user.setFirstName((String) payload.get("given_name"));
                        user.setLastName((String) payload.get("family_name"));
                        user.setId(Constants.USER_ID_PREFIX + UUID.randomUUID().toString().replaceAll("-", ""));
                        user.setRole(RoleEnum.NORMAL.value());
                        user.setPassword(Encryptor.encrypt("AdsCrawlr@123456789"));
                        user.setStatus("active");
                        user = userService.insert(user).get();
                        user.setPassword(null);
                    } else if (user.getGoogleId() == null || user.getGoogleId().isEmpty()) {
                        throw Errors.GOOGLE_EMAIL_EXIST;
                    }

                    jwtRequest.setUsername(payload.getEmail());
                    jwtRequest.setPassword("AdsCrawlr@123456789");
                } else {
                    throw Errors.GOOGLE_EMAIL_NOT_VERIFIED;
                }
            } else {
                throw Errors.GOOGLE_INVALID_ID_TOKEN;
            }
        } else {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtils.generateToken(userDetails);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", user);
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }

}
