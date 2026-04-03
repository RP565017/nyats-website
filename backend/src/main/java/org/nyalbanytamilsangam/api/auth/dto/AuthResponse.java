package org.nyalbanytamilsangam.api.auth.dto;

import lombok.Builder;
import lombok.Data;
import org.nyalbanytamilsangam.api.user.model.Role;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String tokenType = "Bearer";
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
