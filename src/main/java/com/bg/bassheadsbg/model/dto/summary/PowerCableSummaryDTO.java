package com.bg.bassheadsbg.model.dto.summary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class PowerCableSummaryDTO {
    private Long id;
    private String brand;
    private String model;
    private String material;
    private short thickness;
}
