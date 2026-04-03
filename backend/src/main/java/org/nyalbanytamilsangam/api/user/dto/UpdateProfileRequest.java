package org.nyalbanytamilsangam.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String phone;
    private String address;
    private String profileImageUrl;
}
