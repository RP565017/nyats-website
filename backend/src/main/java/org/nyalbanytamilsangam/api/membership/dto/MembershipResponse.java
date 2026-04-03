package org.nyalbanytamilsangam.api.membership.dto;

import lombok.Builder;
import lombok.Data;
import org.nyalbanytamilsangam.api.membership.model.FamilyMember;
import org.nyalbanytamilsangam.api.membership.model.MembershipStatus;
import org.nyalbanytamilsangam.api.membership.model.MembershipType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MembershipResponse {
    private String id;
    private String userId;
    private MembershipType type;
    private MembershipStatus status;
    private Instant startDate;
    private Instant expiryDate;
    private BigDecimal amount;
    private List<FamilyMember> familyMembers;
    private Instant createdAt;
    private Map<String, Object> plan;
}
