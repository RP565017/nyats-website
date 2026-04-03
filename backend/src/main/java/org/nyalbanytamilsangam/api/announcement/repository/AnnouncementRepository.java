package org.nyalbanytamilsangam.api.announcement.repository;

import org.nyalbanytamilsangam.api.announcement.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnnouncementRepository extends MongoRepository<Announcement, String> {
    Page<Announcement> findByActiveTrue(Pageable pageable);
    Page<Announcement> findByActiveTrueAndCategory(String category, Pageable pageable);
}
