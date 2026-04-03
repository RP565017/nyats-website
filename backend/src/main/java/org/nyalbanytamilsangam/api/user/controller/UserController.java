package org.nyalbanytamilsangam.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.security.UserPrincipal;
import org.nyalbanytamilsangam.api.user.dto.UpdateProfileRequest;
import org.nyalbanytamilsangam.api.user.dto.UserProfileDto;
import org.nyalbanytamilsangam.api.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(principal.getId())));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.updateProfile(principal.getId(), request)));
    }
}
