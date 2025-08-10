package com.bg.bassheadsbg.model.entity.images;

import com.bg.bassheadsbg.model.entity.DeviceImageEntity;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.cables.PowerCable;
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
@Table(name = "power_cable_images")
@Getter
@Setter
@NoArgsConstructor
public class PowerCableImage extends BaseEntity implements DeviceImageEntity<PowerCable> {

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "power_cable_id", nullable = false)
    private PowerCable device;

    @Override
    public void setDevice(PowerCable device) {
        this.device = device;
    }
}