package org.nyalbanytamilsangam.api.event.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Pricing {
    private boolean free;
    private BigDecimal memberPrice;
    private BigDecimal nonMemberPrice;
    private String currency;
}
