package com.bg.bassheadsbg.model.entity.images;

import com.bg.bassheadsbg.model.interfaces.DeviceImageEntity;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
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
@Table(name = "midrange_images")
@Getter
@Setter
@NoArgsConstructor
public class MidRangeImage extends BaseEntity implements DeviceImageEntity<MidRange> {

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mid_range_id", nullable = false)
    private MidRange device;


    @Override
    public void setDevice(MidRange device) {
        this.device = device;
    }
}
