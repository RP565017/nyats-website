package org.nyalbanytamilsangam.api.membership.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nyalbanytamilsangam.api.common.audit.AuditableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "memberships")
public class Membership extends AuditableEntity {
    @Id
    private String id;

    private String userId;
    private MembershipType type;
    private MembershipStatus status;

    private Instant startDate;
    private Instant expiryDate;

    private BigDecimal amount;
    private String paymentId;

    private List<FamilyMember> familyMembers;
    private String notes;
}
