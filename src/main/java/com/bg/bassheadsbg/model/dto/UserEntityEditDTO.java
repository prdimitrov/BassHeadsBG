package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.model.entity.City;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserEntityEditDTO {
    private long id;

    @Size(min = 3, max = 30,
            message = "{username.min3max30}")
    @NotBlank(message = "{username.notBlank}")
    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 3, max = 30,
            message = "{firstName.min3max30}")
    @NotBlank(message = "{firstName.notBlank}")
    private String firstName;

    @Size(min = 3, max = 30,
            message = "{lastName.min3max30}")
    @NotBlank(message = "{lastName.notBlank}")
    private String lastName;

    @Email(message = "{email.valid}")
    @NotBlank(message = "email.notBlank")
    @Column(nullable = false, unique = true)
    private String email;

    @Past(message = "{birthDate.mustBeBornInPast}")
    @NotNull(message = "{birthDate.notNull}")
    private LocalDate birthDate;

    private City city;
}
