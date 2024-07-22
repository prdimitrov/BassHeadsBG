package com.bg.bassheadsbg.config;

import com.bg.bassheadsbg.config.custom.CustomAccessDeniedHandler;
import com.bg.bassheadsbg.config.custom.CustomAuthenticationFailureHandler;
import com.bg.bassheadsbg.config.custom.CustomLogoutHandler;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.implementation.BassHeadsDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    private final CustomLogoutHandler customLogoutHandler;

    public SecurityConfig(CustomLogoutHandler customLogoutHandler) {
        this.customLogoutHandler = customLogoutHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/",
                                                "/users/login",
                                                "/users/register",
                                                "/users/login-error",
                                                "/error",
                                                "/api/convert",
                                                "/about",
                                                "/contacts").permitAll()
                                        .requestMatchers("/speakers/high-range/add",
                                                "/speakers/mid-range/add",
                                                "/speakers/subwoofers/add",
                                                "/amplifiers/mono-amp/add",
                                                "/amplifiers/multi-channel-amp/add").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureHandler(authenticationFailureHandler())
                )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/users/logout")
                                        .logoutSuccessUrl("/")
                                        .invalidateHttpSession(true)
                                        .addLogoutHandler(customLogoutHandler)
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

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
