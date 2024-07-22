package com.bg.bassheadsbg.model.entity.users;

import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
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

    @Column(nullable = false)
    private boolean enabled = true;

    public UserEntity() {
        super();
    }

}
