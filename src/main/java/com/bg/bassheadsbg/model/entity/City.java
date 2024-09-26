package com.bg.bassheadsbg.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_key", nullable = false, unique = true)
    private String cityKey;

    @Column(name = "localized_name", nullable = false)
    private String localizedName;

    @Column(name = "administrative_area_localized_name")
    private String administrativeAreaLocalizedName;
}