package org.nyalbanytamilsangam.api.gallery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.nyalbanytamilsangam.api.common.audit.AuditableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "gallery")
public class Gallery extends AuditableEntity {
    @Id
    private String id;

    private String title;

    @Indexed(unique = true)
    private String slug;

    private String description;
    private String coverImageUrl;
    private List<GalleryItem> items;
    private boolean published;
    private String eventId;
    private String createdBy;
    private List<String> tags;
}
