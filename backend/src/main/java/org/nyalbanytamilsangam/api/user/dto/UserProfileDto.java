package org.nyalbanytamilsangam.api.user.dto;

import lombok.Builder;
import lombok.Data;
import org.nyalbanytamilsangam.api.user.model.Role;

import java.time.Instant;

@Data
@Builder
public class UserProfileDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private String profileImageUrl;
    private Instant createdAt;
}
