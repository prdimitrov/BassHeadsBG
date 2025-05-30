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

    private static final String SLASH = "/";
    private static final String USERS = "/users";
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String LOGIN_ERROR = "/login-error";
    private static final String ERROR = "/error";
    private static final String API_CONVERT = "/api/convert";
    private static final String ABOUT = "/about";
    private static final String CONTACTS = "/contacts";
    private static final String REGISTRATION_CONFIRM = "/registrationConfirm";
    private static final String ALL = "/all";
    private static final String ADD_ROLE = "/add-role/**";
    private static final String REMOVE_ROLE = "/remove-role/**";
    private static final String ENABLE = "/enable/**";
    private static final String DISABLE = "/disable/**";
    private static final String SPEAKERS = "/speakers";
    private static final String HIGH_RANGE = "/high-range";
    private static final String MID_RANGE = "/mid-range";
    private static final String SUBWOOFERS = "/subwoofers";
    private static final String AMPLIFIERS = "/amplifiers";
    private static final String MONO_AMPLIFIERS = "/mono-amplifiers";
    private static final String MULTI_CHANNEL_AMPLIFIERS = "/multi-channel-amplifiers";
    private static final String ADD = "/add";
    private static final String EDIT = "/edit/**";
    private static final String DELETE = "/delete/**";
    private static final String CABLES = "/cables";
    private static final String POWER_CABLES = "/power-cables";
    private static final String IMAGES = "/*/images";
    private static final String ADMIN = "ADMIN";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String LOGOUT = "/logout";
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
                                        .requestMatchers(SLASH,
                                                USERS + LOGIN,
                                                USERS + REGISTER,
                                                USERS + LOGIN_ERROR,
                                                ERROR,
                                                API_CONVERT,
                                                ABOUT,
                                                CONTACTS,
                                                USERS + REGISTRATION_CONFIRM).permitAll()
                                        .requestMatchers(USERS + ALL,
                                                USERS + ADD_ROLE,
                                                USERS + REMOVE_ROLE,
                                                USERS + ENABLE,
                                                USERS + DISABLE,
                                                SPEAKERS + HIGH_RANGE + ADD,
                                                SPEAKERS + HIGH_RANGE + EDIT,
                                                SPEAKERS + HIGH_RANGE + DELETE,
                                                SPEAKERS + MID_RANGE + ADD,
                                                SPEAKERS + MID_RANGE + EDIT,
                                                SPEAKERS + MID_RANGE + DELETE,
                                                SPEAKERS + SUBWOOFERS + ADD,
                                                SPEAKERS + SUBWOOFERS + EDIT,
                                                SPEAKERS + SUBWOOFERS + DELETE,
                                                AMPLIFIERS + MONO_AMPLIFIERS + ADD,
                                                AMPLIFIERS + MONO_AMPLIFIERS + EDIT,
                                                AMPLIFIERS + MONO_AMPLIFIERS + DELETE,
                                                AMPLIFIERS + MULTI_CHANNEL_AMPLIFIERS + ADD,
                                                AMPLIFIERS + MULTI_CHANNEL_AMPLIFIERS + EDIT,
                                                AMPLIFIERS + MULTI_CHANNEL_AMPLIFIERS + DELETE,
                                                CABLES + POWER_CABLES + ADD,
                                                CABLES + POWER_CABLES + EDIT,
                                                CABLES + POWER_CABLES + DELETE,
                                                CABLES + IMAGES,
                                                SPEAKERS + IMAGES,
                                                AMPLIFIERS + IMAGES).hasRole(ADMIN)
                                        .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage(USERS + LOGIN)
                                .usernameParameter(USERNAME)
                                .passwordParameter(PASSWORD)
                                .defaultSuccessUrl(SLASH, true)
                                .failureHandler(authenticationFailureHandler())
                )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl(USERS + LOGOUT)
                                        .logoutSuccessUrl(SLASH)
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
