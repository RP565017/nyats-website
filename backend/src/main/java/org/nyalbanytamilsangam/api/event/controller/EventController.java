package org.nyalbanytamilsangam.api.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.event.dto.*;
import org.nyalbanytamilsangam.api.event.model.EventRegistration;
import org.nyalbanytamilsangam.api.event.service.EventService;
import org.nyalbanytamilsangam.api.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<EventResponse>>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(ApiResponse.ok(eventService.getPublishedEvents(page, size, type, search)));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<EventResponse>> getEvent(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.ok(eventService.getEventBySlug(slug)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(
            @Valid @RequestBody CreateEventRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(eventService.createEvent(request, principal.getId())));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(
            @PathVariable String id,
            @RequestBody UpdateEventRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(eventService.updateEvent(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Event deleted"));
    }

    @PostMapping("/{id}/register")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<ApiResponse<EventRegistration>> register(
            @PathVariable String id,
            @RequestBody EventRegistrationRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(eventService.registerForEvent(id, principal.getId(), request), "Registration successful"));
    }
}
