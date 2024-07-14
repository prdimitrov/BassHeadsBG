package com.bg.bassheadsbg.model.entity.users;

import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Size(min = 3, max = 30,
            message = "Username length must be between 3 and 30 characters.")
    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "Please, enter a valid email.")
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 8,
            message = "Password length must be minimum 8 characters.")
    @NotBlank
    private String password;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    private boolean isBanned;

    public UserEntity() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
