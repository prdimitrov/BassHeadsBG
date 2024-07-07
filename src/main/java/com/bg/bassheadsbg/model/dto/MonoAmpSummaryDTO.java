package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.model.enums.AmpClass;

public record MonoAmpSummaryDTO(Long id,
                                String brand,
                                String model,
                                String amplifierClass,
                                int power) {

}