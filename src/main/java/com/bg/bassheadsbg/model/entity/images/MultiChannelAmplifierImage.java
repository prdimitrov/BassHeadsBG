package com.bg.bassheadsbg.model.entity.images;

import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
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
@Table(name = "multichannelamplifier_images")
@Getter
@Setter
@NoArgsConstructor
public class MultiChannelAmplifierImage extends BaseEntity {

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "multi_channel_amplifier_id", nullable = false)
    private MultiChannelAmplifier multiChannelAmplifier;
}
