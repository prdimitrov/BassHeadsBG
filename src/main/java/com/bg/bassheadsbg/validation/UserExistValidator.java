package com.bg.bassheadsbg.validation;


import com.bg.bassheadsbg.model.dto.UserLoginDTO;
import com.bg.bassheadsbg.model.entity.users.User;
import com.bg.bassheadsbg.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserExistValidator implements ConstraintValidator<UserExist, UserLoginDTO> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserExistValidator(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initialize(UserExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserLoginDTO userLoginDTO, ConstraintValidatorContext context) {
        Optional<User> optionalUser = userService.findByUsername(userLoginDTO.getUsername());
        if (optionalUser.isEmpty()){
            return false;
        }
        String  rawPassword = userLoginDTO.getPassword();
        String  encodedPassword  = optionalUser.get().getPassword();

        return passwordEncoder.matches(rawPassword, encodedPassword);

    }
}
