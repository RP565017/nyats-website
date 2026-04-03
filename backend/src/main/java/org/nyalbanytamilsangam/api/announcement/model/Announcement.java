package org.nyalbanytamilsangam.api.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nyalbanytamilsangam.api.common.audit.AuditableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "announcements")
public class Announcement extends AuditableEntity {
    @Id
    private String id;
    private String title;
    private String content;
    private String category;
    private boolean pinned;
    private boolean active;
    private Instant expiresAt;
    private String createdBy;
    private String imageUrl;
}
