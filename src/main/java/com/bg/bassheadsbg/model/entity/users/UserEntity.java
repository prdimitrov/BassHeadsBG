package com.bg.bassheadsbg.model.entity.users;

import com.bg.bassheadsbg.model.entity.City;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
            message = "{username.min3max30}")
    @NotBlank(message = "{username.notBlank}")
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "{email.valid}")
    @NotBlank(message = "email.notBlank")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 8,
            message = "{password.sizeMin8}")
    @NotBlank(message = "{password.notBlank}")
    private String password;

    @Size(min = 3, max = 30,
            message = "{firstName.min3max30}")
    @NotBlank(message = "{firstName.notBlank}")
    private String firstName;

    @Size(min = 3, max = 30,
            message = "{lastName.min3max30}")
    @NotBlank(message = "{lastName.notBlank}")
    private String lastName;

    @Past(message = "{birthDate.mustBeBornInPast}")
    @NotNull(message = "{birthDate.notNull}")
    private LocalDate birthDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(nullable = false)
    private boolean enabled = true;

    public UserEntity() {
        super();
    }

}
