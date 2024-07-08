package com.bg.bassheadsbg.config;

import com.bg.bassheadsbg.config.custom.CustomAccessDeniedHandler;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.impl.BassHeadsDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        // Setup which URL-s are available to who
                        authorizeRequests ->
                                authorizeRequests
                                        // all static resources to "common locations" (css, images, js) are available to anyone
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        // some more resources for all users
                                        .requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll()
                                        // all other URL-s should be authenticated.
                                        .requestMatchers("/speakers/**", "/amplifiers/**").hasRole("ADMIN")
                                        .anyRequest().authenticated()

                )
                .formLogin(formLogin ->
                        formLogin
                                // Where is our custom login form?
                                .loginPage("/users/login")
                                // what is the name of the username parameter in the Login POST request?
                                .usernameParameter("username")
                                // what is the name of the password parameter in the Login POST request?
                                .passwordParameter("password")
                                // What will happen if the login is successful
                                .defaultSuccessUrl("/", true)
                                // What will happen if the login fails
                                .failureForwardUrl("/users/login-error")
                )
                .logout(
                        logout ->
                                logout
                                        // what is the logout URL?
                                        .logoutUrl("/users/logout")
                                        // Where to go after successful logout?
                                        .logoutSuccessUrl("/")
                                        // invalidate the session after logout.
                                        .invalidateHttpSession(true)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(accessDeniedHandler())
                )
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    @Bean
    public BassHeadsDetailsService userDetailsService(UserRepository userRepository) {
        return new BassHeadsDetailsService(userRepository);
    }
}
