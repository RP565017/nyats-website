package org.nyalbanytamilsangam.api.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nyalbanytamilsangam.api.common.audit.AuditableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "payments")
public class Payment extends AuditableEntity {
    @Id
    private String id;
    private String userId;
    private PaymentType type;
    private PaymentStatus status;
    private BigDecimal amount;
    private String currency;
    private String stripePaymentIntentId;
    private String stripeClientSecret;
    private String referenceId;
    private String description;
}
