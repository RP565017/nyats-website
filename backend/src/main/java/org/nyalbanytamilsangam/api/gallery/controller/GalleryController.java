package org.nyalbanytamilsangam.api.gallery.controller;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.gallery.model.Gallery;
import org.nyalbanytamilsangam.api.gallery.service.GalleryService;
import org.nyalbanytamilsangam.api.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<Gallery>>> getGalleries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return ResponseEntity.ok(ApiResponse.ok(galleryService.getGalleries(page, size)));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<Gallery>> getAlbum(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.ok(galleryService.getBySlug(slug)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Gallery>> createAlbum(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(galleryService.createAlbum(body, principal.getId())));
    }

    @PostMapping("/{id}/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Gallery>> uploadImage(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(ApiResponse.ok(galleryService.uploadToAlbum(id, file)));
    }
}
