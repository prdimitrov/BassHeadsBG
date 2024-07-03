package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mid_range")
public class MidRange extends BaseSpeaker {

    public MidRange() {
        super();
    }
}
