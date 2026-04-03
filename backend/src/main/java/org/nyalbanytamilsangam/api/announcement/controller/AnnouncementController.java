package org.nyalbanytamilsangam.api.announcement.controller;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.announcement.model.Announcement;
import org.nyalbanytamilsangam.api.announcement.service.AnnouncementService;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<Announcement>>> getAnnouncements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(announcementService.getAnnouncements(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Announcement>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(announcementService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Announcement>> create(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(announcementService.createAnnouncement(body, principal.getId())));
    }
}
