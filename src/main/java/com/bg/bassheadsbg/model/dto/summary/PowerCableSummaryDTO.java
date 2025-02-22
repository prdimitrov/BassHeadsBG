package com.bg.bassheadsbg.model.dto.summary;

import com.bg.bassheadsbg.model.entity.images.PowerCableImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class PowerCableSummaryDTO {
    private Long id;
    private String brand;
    private String model;
    private String material;
    private short thickness;
    private String imageFile;
    private long likes;
}
