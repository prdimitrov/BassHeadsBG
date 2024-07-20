package com.bg.bassheadsbg.model.dto.summary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class MidRangeSummaryDTO {
    private Long id;
    private String brand;
    private String model;
    private float size;
    private short powerHandling;
    private List<String> images;
    private int userLikes;
}