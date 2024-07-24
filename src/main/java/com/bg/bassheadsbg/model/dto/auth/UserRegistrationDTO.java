package com.bg.bassheadsbg.model.dto.auth;

import com.bg.bassheadsbg.validation.fieldsMatcher.FieldsMatch;
import com.bg.bassheadsbg.validation.uniqueEmail.UniqueUserEmail;
import com.bg.bassheadsbg.validation.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@FieldsMatch(first = "password", second = "confirmPassword", message = "{password.match}")
public class UserRegistrationDTO {
    @UniqueUsername(message = "{username.inUse}")
    @Size(min = 3, max = 30,
            message = "{username.min3max30}")
    @NotBlank
    private String username;

    @UniqueUserEmail(message = "{email.inUse}")
    @Email(message = "{email.valid}")
    @NotBlank(message = "{email.NotBlank}")
    private String email;

    @Size(min = 8,
            message = "{password.sizeMin8}")
    @NotBlank(message = "{password.notBlank}")
    private String password;

    @Size(min = 8,
            message = "{password.sizeMin8}")
    @NotBlank(message = "{password.notBlank}")
    private String confirmPassword;

    @Size(min = 3, max = 30,
            message = "{firstName.min3max30}")
    @NotBlank(message = "firstName.notBlank")
    private String firstName;

    @Size(min = 3, max = 30,
            message = "{lastName.min3max30}")
    @NotBlank(message = "{lastName.notBlank}")
    private String lastName;

    @Past(message = "{birthDate.mustBeBornInPast}")
    @NotNull(message = "{birthDate.notNull}")
    private LocalDate birthDate;
}
