package org.nyalbanytamilsangam.api.gallery.repository;

import org.nyalbanytamilsangam.api.gallery.model.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GalleryRepository extends MongoRepository<Gallery, String> {
    Optional<Gallery> findBySlug(String slug);
    Page<Gallery> findByPublishedTrue(Pageable pageable);
}
