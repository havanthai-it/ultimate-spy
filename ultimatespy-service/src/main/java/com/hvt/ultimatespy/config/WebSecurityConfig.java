package com.hvt.ultimatespy.config;

import com.hvt.ultimatespy.filters.UserSubscriptionFilter;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.enums.RoleEnum;
import com.hvt.ultimatespy.controllers.jwt.JwtAuthenticationEntryPoint;
import com.hvt.ultimatespy.filters.CorsFilter;
import com.hvt.ultimatespy.filters.JwtRequestFilter;
import com.hvt.ultimatespy.services.jwt.JwtUserDetailsService;
import org.elasticsearch.common.collect.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserSubscriptionFilter userSubscriptionFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example 
        httpSecurity.cors().configurationSource(
                request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://127.0.0.1:4200", "http://143.110.146.166:80", "https://143.110.146.166:443", "http://adscrawlr.com", "https://adscrawlr.com"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(List.of("*"));
                    return cors;
                }).and()
                .csrf().disable()
                // Don't authenticate this particular request
                .authorizeRequests()
                .antMatchers(Constants.ROUTE_AUTHENTICATE).permitAll()
                .antMatchers(Constants.ROUTE_USER).permitAll()
                .antMatchers(Constants.ROUTE_USER_CONFIRM_ID).permitAll()
                .antMatchers(Constants.ROUTE_SUBSCRIBER_EMAIL).permitAll()
                .antMatchers(Constants.ROUTE_SUBSCRIPTION_PLAN).permitAll()
                .antMatchers(Constants.ROUTE_USER_FORGOT_PASSWORD).permitAll()
                .antMatchers(Constants.ROUTE_USER_RESET_PASSWORD).permitAll()
                .antMatchers(Constants.ROUTE_POST_FACEBOOK).permitAll()
                .antMatchers(Constants.ROUTE_POST_FACEBOOK + "/*").permitAll()
                .antMatchers(Constants.ROUTE_POST_FACEBOOK_SEARCH).permitAll()
                .antMatchers(Constants.ROUTE_REFERRAL).permitAll()
                .antMatchers(Constants.ROUTE_ADMIN).hasRole(RoleEnum.ADMIN.value())
                // All other requests need to be authenticated
                .anyRequest().authenticated().and()
                // Make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(userSubscriptionFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
