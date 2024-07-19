package com.bg.bassheadsbg.model.entity;

import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "ex_rates")
public class ExRateEntity extends BaseEntity {

    @NotEmpty
    @Column(unique = true)
    private String currency;

    @Positive
    @NotNull
    private BigDecimal rate;

    public ExRateEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ExRateEntity setRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public String toString() {
        return "ExRateEntity{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }
}