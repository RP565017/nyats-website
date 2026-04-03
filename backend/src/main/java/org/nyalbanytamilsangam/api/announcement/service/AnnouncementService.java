package org.nyalbanytamilsangam.api.announcement.service;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.announcement.model.Announcement;
import org.nyalbanytamilsangam.api.announcement.repository.AnnouncementRepository;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.common.exception.ResourceNotFoundException;
import org.nyalbanytamilsangam.api.config.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Cacheable(value = CacheConfig.ANNOUNCEMENTS_CACHE, key = "#page + '_' + #size")
    public PagedResponse<Announcement> getAnnouncements(int page, int size) {
        Page<Announcement> p = announcementRepository.findByActiveTrue(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "pinned", "createdAt")));
        return PagedResponse.<Announcement>builder()
                .content(p.getContent()).totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages()).number(p.getNumber()).size(p.getSize())
                .first(p.isFirst()).last(p.isLast()).build();
    }

    @CacheEvict(value = CacheConfig.ANNOUNCEMENTS_CACHE, allEntries = true)
    public Announcement createAnnouncement(Map<String, Object> body, String userId) {
        Announcement a = Announcement.builder()
                .title((String) body.get("title"))
                .content((String) body.get("content"))
                .category((String) body.getOrDefault("category", "GENERAL"))
                .pinned(Boolean.TRUE.equals(body.get("pinned")))
                .active(true)
                .createdBy(userId)
                .imageUrl((String) body.get("imageUrl"))
                .build();
        return announcementRepository.save(a);
    }

    public Announcement getById(String id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement", id));
    }
}
