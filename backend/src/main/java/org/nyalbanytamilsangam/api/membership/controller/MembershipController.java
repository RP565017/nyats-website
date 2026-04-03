package org.nyalbanytamilsangam.api.membership.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.membership.dto.MembershipRequest;
import org.nyalbanytamilsangam.api.membership.dto.MembershipResponse;
import org.nyalbanytamilsangam.api.membership.service.MembershipService;
import org.nyalbanytamilsangam.api.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping("/plans")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getPlans() {
        return ResponseEntity.ok(ApiResponse.ok(membershipService.getPlans()));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<MembershipResponse>> subscribe(
            @Valid @RequestBody MembershipRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(membershipService.subscribe(principal.getId(), request), "Membership activated"));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<MembershipResponse>> getMyMembership(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(membershipService.getMyMembership(principal.getId())));
    }
}
