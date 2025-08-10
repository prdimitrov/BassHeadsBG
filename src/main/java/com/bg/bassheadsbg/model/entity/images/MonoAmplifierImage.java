package com.bg.bassheadsbg.model.entity.images;

import com.bg.bassheadsbg.model.interfaces.DeviceImageEntity;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
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
@Table(name = "monoamplifier_images")
@Getter
@Setter
@NoArgsConstructor
public class MonoAmplifierImage extends BaseEntity implements DeviceImageEntity<MonoAmplifier> {

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mono_amplifier_id", nullable = false)
    private MonoAmplifier device;

    @Override
    public void setDevice(MonoAmplifier device) {
        this.device = device;
    }
}
