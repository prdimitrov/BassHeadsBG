package com.bg.bassheadsbg.model.dto.details;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CableDetailsDTO {
    private long id;
    private String brand;
    private String model;
    private double length;
    private String material;
    private String cableType;
    private short thickness;
    private String description;
}
