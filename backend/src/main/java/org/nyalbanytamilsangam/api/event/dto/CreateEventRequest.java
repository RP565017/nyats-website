package org.nyalbanytamilsangam.api.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.nyalbanytamilsangam.api.event.model.EventType;
import org.nyalbanytamilsangam.api.event.model.Pricing;
import org.nyalbanytamilsangam.api.event.model.Venue;

import java.time.Instant;
import java.util.List;

@Data
public class CreateEventRequest {
    @NotBlank private String title;
    private String description;
    private String shortDescription;
    @NotNull private EventType type;
    @NotNull private Instant startDate;
    private Instant endDate;
    private Venue venue;
    private Pricing pricing;
    private int maxCapacity;
    private String coverImageUrl;
    private List<String> tags;
    private boolean featured;
}
