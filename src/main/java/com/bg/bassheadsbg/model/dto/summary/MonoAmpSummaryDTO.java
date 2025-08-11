package com.bg.bassheadsbg.model.dto.summary;

import com.bg.bassheadsbg.model.interfaces.DeviceSummaryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class MonoAmpSummaryDTO implements DeviceSummaryDTO {
    private Long id;
    private String brand;
    private String model;
    private String amplifierClass;
    private int power;
    private String imageFile;
    private long likes;
}