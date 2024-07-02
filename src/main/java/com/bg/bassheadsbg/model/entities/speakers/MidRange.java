package com.bg.bassheadsbg.model.entities.speakers;

import com.bg.bassheadsbg.model.entities.base.BaseSpeaker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mid_range")
public class MidRange extends BaseSpeaker {

    public MidRange() {
        super();
    }
}
