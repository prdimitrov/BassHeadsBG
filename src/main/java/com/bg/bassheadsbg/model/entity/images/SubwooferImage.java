package com.bg.bassheadsbg.model.entity.images;

import com.bg.bassheadsbg.model.interfaces.DeviceImageEntity;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
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
@Table(name = "subwoofer_images")
@Getter
@Setter
@NoArgsConstructor
public class SubwooferImage extends BaseEntity implements DeviceImageEntity<Subwoofer> {

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subwoofer_id", nullable = false)
    private Subwoofer device;

    @Override
    public void setDevice(Subwoofer device) {
        this.device = device;
    }
}
