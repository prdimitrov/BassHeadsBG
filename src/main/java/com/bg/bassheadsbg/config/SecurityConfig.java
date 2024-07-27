package com.bg.bassheadsbg.config;

import com.bg.bassheadsbg.config.custom.CustomAccessDeniedHandler;
import com.bg.bassheadsbg.config.custom.CustomAuthenticationFailureHandler;
import com.bg.bassheadsbg.config.custom.CustomLogoutHandler;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.implementation.BassHeadsDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    private final CustomLogoutHandler customLogoutHandler;

    public SecurityConfig(@Lazy CustomLogoutHandler customLogoutHandler) {
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
                                        .requestMatchers("/users/all",
                                                "/users/add-role/**",
                                                "/users/remove-role/**",
                                                "/users/enable/**",
                                                "/users/disable/**",
                                                "/speakers/high-range/add",
                                                "/speakers/high-range/edit/**",
                                                "/speakers/high-range/delete/**",
                                                "/speakers/mid-range/add",
                                                "/speakers/mid-range/edit/**",
                                                "/speakers/mid-range/delete/**",
                                                "/speakers/subwoofers/add",
                                                "/speakers/subwoofers/edit/**",
                                                "/speakers/subwoofers/delete/**",
                                                "/amplifiers/mono-amplifiers/add",
                                                "/amplifiers/mono-amplifiers/edit/**",
                                                "/amplifiers/mono-amplifiers/delete/**",
                                                "/amplifiers/multi-channel-amplifiers/add",
                                                "/amplifiers/multi-channel-amplifiers/edit/**",
                                                "/amplifiers/multi-channel-amplifiers/delete/**").hasRole("ADMIN")
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
