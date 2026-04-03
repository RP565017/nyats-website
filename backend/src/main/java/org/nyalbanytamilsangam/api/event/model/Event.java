package org.nyalbanytamilsangam.api.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nyalbanytamilsangam.api.common.audit.AuditableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "events")
public class Event extends AuditableEntity {
    @Id
    private String id;

    private String title;

    @Indexed(unique = true)
    private String slug;

    private String description;
    private String shortDescription;

    private EventType type;
    private EventStatus status;

    private Instant startDate;
    private Instant endDate;
    private String registrationDeadline;

    private Venue venue;
    private Pricing pricing;

    private int maxCapacity;
    private int registeredCount;

    private String coverImageUrl;
    private List<String> imageUrls;
    private List<String> tags;

    private String createdBy;
    private boolean featured;
}
