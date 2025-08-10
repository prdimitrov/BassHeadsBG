package com.bg.bassheadsbg.model.entity.images;

import com.bg.bassheadsbg.model.entity.DeviceImageEntity;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "highrange_images")
@Getter
@Setter
@NoArgsConstructor
public class HighRangeImage extends BaseEntity implements DeviceImageEntity<HighRange> {

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "high_range_id", nullable = false)
    private HighRange device;


    @Override
    public void setDevice(HighRange device) {
        this.device = device;
    }
}
