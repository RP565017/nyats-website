package org.nyalbanytamilsangam.api.gallery.model;

import lombok.Data;

@Data
public class GalleryItem {
    private String publicId;
    private String url;
    private String thumbnailUrl;
    private String caption;
    private String mimeType;
    private long sizeBytes;
}
