package org.nyalbanytamilsangam.api.gallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.nyalbanytamilsangam.api.gallery.model.GalleryItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryStorageService {

    private final Cloudinary cloudinary;

    public CloudinaryStorageService(@Value("${cloudinary.url}") String cloudinaryUrl) {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }

    public GalleryItem uploadImage(MultipartFile file, String folder) throws IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds 10MB limit");
        }
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "nyats/" + folder,
                "resource_type", "auto",
                "quality", "auto",
                "fetch_format", "auto"
        ));

        GalleryItem item = new GalleryItem();
        item.setPublicId((String) result.get("public_id"));
        item.setUrl((String) result.get("secure_url"));
        item.setThumbnailUrl(buildThumbnailUrl((String) result.get("secure_url")));
        item.setMimeType(file.getContentType());
        item.setSizeBytes(file.getSize());
        return item;
    }

    private String buildThumbnailUrl(String url) {
        return url.replace("/upload/", "/upload/w_400,h_300,c_fill,q_auto,f_auto/");
    }
}
