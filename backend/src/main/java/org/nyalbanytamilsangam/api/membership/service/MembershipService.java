package org.nyalbanytamilsangam.api.membership.service;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.exception.ResourceNotFoundException;
import org.nyalbanytamilsangam.api.config.CacheConfig;
import org.nyalbanytamilsangam.api.membership.dto.MembershipRequest;
import org.nyalbanytamilsangam.api.membership.dto.MembershipResponse;
import org.nyalbanytamilsangam.api.membership.model.Membership;
import org.nyalbanytamilsangam.api.membership.model.MembershipStatus;
import org.nyalbanytamilsangam.api.membership.model.MembershipType;
import org.nyalbanytamilsangam.api.membership.repository.MembershipRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    private static final Map<MembershipType, Map<String, Object>> PLANS = Map.of(
        MembershipType.INDIVIDUAL, Map.of("name", "Individual", "price", 50, "duration", 12, "description", "Single person membership"),
        MembershipType.FAMILY, Map.of("name", "Family", "price", 100, "duration", 12, "description", "Family membership (up to 4 members)"),
        MembershipType.STUDENT, Map.of("name", "Student", "price", 25, "duration", 12, "description", "Student membership"),
        MembershipType.SENIOR, Map.of("name", "Senior", "price", 30, "duration", 12, "description", "Senior membership (65+)"),
        MembershipType.LIFETIME, Map.of("name", "Lifetime", "price", 500, "duration", 0, "description", "Lifetime membership")
    );

    @Cacheable(CacheConfig.MEMBERSHIP_PLANS_CACHE)
    public List<Map<String, Object>> getPlans() {
        return PLANS.entrySet().stream()
                .map(e -> { var m = new HashMap<String, Object>(e.getValue()); m.put("type", e.getKey()); return (Map<String, Object>) m; })
                .toList();
    }

    public MembershipResponse subscribe(String userId, MembershipRequest request) {
        Map<String, Object> plan = PLANS.get(request.getType());
        int durationMonths = (int) plan.get("duration");
        Instant now = Instant.now();
        Instant expiry = durationMonths > 0 ? now.plus(durationMonths * 30L, ChronoUnit.DAYS) : now.plus(100 * 365L, ChronoUnit.DAYS);

        Membership membership = Membership.builder()
                .userId(userId).type(request.getType()).status(MembershipStatus.ACTIVE)
                .startDate(now).expiryDate(expiry)
                .amount(BigDecimal.valueOf((int) plan.get("price")))
                .paymentId(request.getPaymentId())
                .familyMembers(request.getFamilyMembers())
                .notes(request.getNotes()).build();
        membership = membershipRepository.save(membership);
        return toResponse(membership, plan);
    }

    public MembershipResponse getMyMembership(String userId) {
        Membership m = membershipRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No active membership found"));
        return toResponse(m, PLANS.get(m.getType()));
    }

    private MembershipResponse toResponse(Membership m, Map<String, Object> plan) {
        return MembershipResponse.builder()
                .id(m.getId()).userId(m.getUserId()).type(m.getType()).status(m.getStatus())
                .startDate(m.getStartDate()).expiryDate(m.getExpiryDate()).amount(m.getAmount())
                .familyMembers(m.getFamilyMembers()).createdAt(m.getCreatedAt()).plan(plan).build();
    }
}
