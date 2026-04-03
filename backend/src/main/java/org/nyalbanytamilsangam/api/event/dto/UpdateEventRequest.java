package org.nyalbanytamilsangam.api.event.dto;

import lombok.Data;
import org.nyalbanytamilsangam.api.event.model.EventStatus;
import org.nyalbanytamilsangam.api.event.model.EventType;
import org.nyalbanytamilsangam.api.event.model.Pricing;
import org.nyalbanytamilsangam.api.event.model.Venue;

import java.time.Instant;
import java.util.List;

@Data
public class UpdateEventRequest {
    private String title;
    private String description;
    private String shortDescription;
    private EventType type;
    private EventStatus status;
    private Instant startDate;
    private Instant endDate;
    private Venue venue;
    private Pricing pricing;
    private int maxCapacity;
    private String coverImageUrl;
    private List<String> tags;
    private boolean featured;
}
