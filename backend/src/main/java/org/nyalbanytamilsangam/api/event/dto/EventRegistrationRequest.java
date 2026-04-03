package org.nyalbanytamilsangam.api.event.dto;

import lombok.Data;

@Data
public class EventRegistrationRequest {
    private int attendeeCount = 1;
    private String notes;
}
