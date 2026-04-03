package org.nyalbanytamilsangam.api.gallery.service;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.common.exception.ResourceNotFoundException;
import org.nyalbanytamilsangam.api.common.util.SlugUtil;
import org.nyalbanytamilsangam.api.config.CacheConfig;
import org.nyalbanytamilsangam.api.gallery.model.Gallery;
import org.nyalbanytamilsangam.api.gallery.model.GalleryItem;
import org.nyalbanytamilsangam.api.gallery.repository.GalleryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final CloudinaryStorageService storageService;

    @Cacheable(value = CacheConfig.GALLERY_CACHE, key = "#page + '_' + #size")
    public PagedResponse<Gallery> getGalleries(int page, int size) {
        Page<Gallery> p = galleryRepository.findByPublishedTrue(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        return PagedResponse.<Gallery>builder()
                .content(p.getContent()).totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages()).number(p.getNumber()).size(p.getSize())
                .first(p.isFirst()).last(p.isLast()).build();
    }

    public Gallery getBySlug(String slug) {
        return galleryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery not found: " + slug));
    }

    @CacheEvict(value = CacheConfig.GALLERY_CACHE, allEntries = true)
    public Gallery createAlbum(Map<String, Object> body, String userId) {
        String title = (String) body.get("title");
        Gallery gallery = Gallery.builder()
                .title(title).slug(SlugUtil.toSlug(title))
                .description((String) body.get("description"))
                .published(false).createdBy(userId)
                .items(new ArrayList<>()).build();
        return galleryRepository.save(gallery);
    }

    @CacheEvict(value = CacheConfig.GALLERY_CACHE, allEntries = true)
    public Gallery uploadToAlbum(String albumId, MultipartFile file) throws IOException {
        Gallery gallery = galleryRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery", albumId));
        GalleryItem item = storageService.uploadImage(file, "gallery/" + albumId);
        if (gallery.getItems() == null) gallery.setItems(new ArrayList<>());
        gallery.getItems().add(item);
        if (gallery.getCoverImageUrl() == null) gallery.setCoverImageUrl(item.getUrl());
        return galleryRepository.save(gallery);
    }
}
