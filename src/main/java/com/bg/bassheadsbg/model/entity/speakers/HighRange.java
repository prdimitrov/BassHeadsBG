package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker {

    public HighRange() {
        super();
    }
}
