package com.bg.bassheadsbg.model.dto.summary;

import com.bg.bassheadsbg.util.ValueFormatterUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class MidRangeSummaryDTO {
    private Long id;
    private String brand;
    private String model;
    private float size;
    private short powerHandling;
    private String imageFile;
    private long likes;

    public String getSize() {
        return ValueFormatterUtil.formatValue(this.size);
    }
}