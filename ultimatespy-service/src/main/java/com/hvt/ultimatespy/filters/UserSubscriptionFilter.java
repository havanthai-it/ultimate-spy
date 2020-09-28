package com.hvt.ultimatespy.filters;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.enums.RoleEnum;
import com.hvt.ultimatespy.utils.jwt.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserSubscriptionFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request.getRequestURI().equals("path_need_to_check_subscription")) {

            final String requestTokenHeader = request.getHeader("Authorization");

            String username = null;
            String jwtToken = null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and value
            // only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtTokenUtils.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to value JWT Token");
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired");
                }
            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {

                User user = null;
                try {
                    user = userService.getByEmail(username).get();
                    if (!user.getRole().equals(RoleEnum.ADMIN.value()) && (user.getLstSubscriptions() == null || user.getLstSubscriptions().size() == 0)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    }
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
