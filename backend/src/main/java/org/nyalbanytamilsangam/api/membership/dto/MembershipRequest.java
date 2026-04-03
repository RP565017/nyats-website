package org.nyalbanytamilsangam.api.membership.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.nyalbanytamilsangam.api.membership.model.FamilyMember;
import org.nyalbanytamilsangam.api.membership.model.MembershipType;

import java.util.List;

@Data
public class MembershipRequest {
    @NotNull
    private MembershipType type;
    private List<FamilyMember> familyMembers;
    private String paymentId;
    private String notes;
}
