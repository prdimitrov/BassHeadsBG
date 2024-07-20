package com.bg.bassheadsbg.model.dto.summary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class MultiChannelAmpSummaryDTO {
    private Long id;
    private String brand;
    private String model;
    private String amplifierClass;
    private int power;
    private List<String> images;
    private long likes;
}