package org.nyalbanytamilsangam.api.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nyalbanytamilsangam.api.common.audit.AuditableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "event_registrations")
@CompoundIndex(name = "user_event_idx", def = "{'userId': 1, 'eventId': 1}", unique = true)
public class EventRegistration extends AuditableEntity {
    @Id
    private String id;
    private String eventId;
    private String userId;
    private String paymentId;
    private String status;
    private int attendeeCount;
    private String notes;
}
