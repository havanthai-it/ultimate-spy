package com.hvt.ultimatespy.controllers.jwt;

import com.hvt.ultimatespy.models.jwt.JwtRequest;
import com.hvt.ultimatespy.models.jwt.JwtResponse;
import com.hvt.ultimatespy.services.jwt.JwtUserDetailsService;
import com.hvt.ultimatespy.utils.Errors;
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

import java.util.Base64;

@RestController
@RequestMapping(value = "/api/authenticate")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authorize(@RequestBody JwtRequest jwtRequest) throws Exception {
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

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
