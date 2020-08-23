package com.hvt.ultimatespy.services.jwt;

import com.hvt.ultimatespy.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(JwtUserDetailsService.class.getName());

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.hvt.ultimatespy.models.user.User user = null;
        try {
            user = userService.getByEmail(username).get();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

        if (user != null) {
            Collection<GrantedAuthority> lstGa = new ArrayList<>();
            if (user.getRole() != null) {
                lstGa.add(new SimpleGrantedAuthority(user.getRole()));
            }
            return new User(user.getEmail(), user.getPassword(), lstGa);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }

}
