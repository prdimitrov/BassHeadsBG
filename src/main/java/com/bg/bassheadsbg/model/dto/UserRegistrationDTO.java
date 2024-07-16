package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.validation.fieldsMatcher.FieldsMatch;
import com.bg.bassheadsbg.validation.uniqueEmail.UniqueUserEmail;
import com.bg.bassheadsbg.validation.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@FieldsMatch(first = "password", second = "confirmPassword")
public class UserRegistrationDTO {
    @UniqueUsername(message = "Username already in use.")
    @Size(min = 3, max = 30,
            message = "Username length must be between 3 and 30 characters.")
    @NotBlank
    private String username;

    @UniqueUserEmail(message = "Email already in use.")
    @Email(message = "Please, enter a valid email.")
    @NotBlank
    private String email;

    @Size(min = 8,
            message = "Password length must be minimum 8 characters.")
    @NotBlank
    private String password;

    @Size(min = 8,
            message = "Password length must be minimum 8 characters.")
    @NotBlank
    private String confirmPassword;

    @Size(min = 3, max = 30,
            message = "Name length must be between 3 and 30 characters.")
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 30,
            message = "Last name length must be between 3 and 30 characters.")
    @NotBlank
    private String lastName;

    @Past(message = "You must be born in the past.")
    @NotNull
    private LocalDate birthDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
