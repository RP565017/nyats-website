package org.nyalbanytamilsangam.api.admin.controller;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.admin.service.DashboardService;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.membership.model.Membership;
import org.nyalbanytamilsangam.api.membership.model.MembershipStatus;
import org.nyalbanytamilsangam.api.membership.repository.MembershipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService dashboardService;
    private final MembershipRepository membershipRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.ok(dashboardService.getDashboardStats()));
    }

    @GetMapping("/memberships")
    public ResponseEntity<ApiResponse<PagedResponse<Membership>>> getAllMemberships(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        Page<Membership> p;
        if (status != null) {
            p = membershipRepository.findByStatus(MembershipStatus.valueOf(status.toUpperCase()),
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        } else {
            p = membershipRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        }
        return ResponseEntity.ok(ApiResponse.ok(PagedResponse.<Membership>builder()
                .content(p.getContent()).totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages()).number(p.getNumber()).size(p.getSize())
                .first(p.isFirst()).last(p.isLast()).build()));
    }
}
