package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseSpeaker extends BaseEntity {

    @Positive
    private int price;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
    @NotNull
    private float sensitivity;

    @Positive
    @NotNull
    private float size;

    @PositiveOrZero
    @NotNull
    private float frequencyResponse;

    @PositiveOrZero
    @NotNull
    private byte numberOfCoils;

    @PositiveOrZero
    @NotNull
    private float impedance;

    @PositiveOrZero
    @NotNull
    private short powerHandling;

    @ValidUrlList
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "images")
    private List<@URL @NotBlank String> images = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "likes")
    private Set<UserEntity> userLikes = new HashSet<>();

    public long getLikes() {
        return this.userLikes.size();
    }

}
