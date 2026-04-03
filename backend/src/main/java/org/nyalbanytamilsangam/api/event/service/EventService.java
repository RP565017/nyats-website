package org.nyalbanytamilsangam.api.event.service;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.common.exception.BadRequestException;
import org.nyalbanytamilsangam.api.common.exception.ResourceNotFoundException;
import org.nyalbanytamilsangam.api.common.util.SlugUtil;
import org.nyalbanytamilsangam.api.config.CacheConfig;
import org.nyalbanytamilsangam.api.event.dto.CreateEventRequest;
import org.nyalbanytamilsangam.api.event.dto.EventRegistrationRequest;
import org.nyalbanytamilsangam.api.event.dto.EventResponse;
import org.nyalbanytamilsangam.api.event.dto.UpdateEventRequest;
import org.nyalbanytamilsangam.api.event.model.Event;
import org.nyalbanytamilsangam.api.event.model.EventRegistration;
import org.nyalbanytamilsangam.api.event.model.EventStatus;
import org.nyalbanytamilsangam.api.event.model.EventType;
import org.nyalbanytamilsangam.api.event.repository.EventRegistrationRepository;
import org.nyalbanytamilsangam.api.event.repository.EventRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;

    @Cacheable(value = CacheConfig.EVENTS_CACHE, key = "#page + '_' + #size + '_' + #type + '_' + #search")
    public PagedResponse<EventResponse> getPublishedEvents(int page, int size, String type, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "startDate"));
        Page<Event> eventsPage;

        if (search != null && !search.isBlank()) {
            eventsPage = eventRepository.searchByStatusAndKeyword(EventStatus.PUBLISHED, search, pageable);
        } else if (type != null && !type.isBlank()) {
            eventsPage = eventRepository.findByStatusAndType(EventStatus.PUBLISHED, EventType.valueOf(type.toUpperCase()), pageable);
        } else {
            eventsPage = eventRepository.findByStatus(EventStatus.PUBLISHED, pageable);
        }

        return PagedResponse.<EventResponse>builder()
                .content(eventsPage.getContent().stream().map(this::toResponse).toList())
                .totalElements(eventsPage.getTotalElements())
                .totalPages(eventsPage.getTotalPages())
                .number(eventsPage.getNumber())
                .size(eventsPage.getSize())
                .first(eventsPage.isFirst())
                .last(eventsPage.isLast())
                .build();
    }

    public EventResponse getEventBySlug(String slug) {
        Event event = eventRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with slug: " + slug));
        return toResponse(event);
    }

    @CacheEvict(value = CacheConfig.EVENTS_CACHE, allEntries = true)
    public EventResponse createEvent(CreateEventRequest request, String userId) {
        String slug = SlugUtil.toSlug(request.getTitle());
        Event event = Event.builder()
                .title(request.getTitle())
                .slug(slug)
                .description(request.getDescription())
                .shortDescription(request.getShortDescription())
                .type(request.getType())
                .status(EventStatus.DRAFT)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .venue(request.getVenue())
                .pricing(request.getPricing())
                .maxCapacity(request.getMaxCapacity())
                .coverImageUrl(request.getCoverImageUrl())
                .tags(request.getTags())
                .featured(request.isFeatured())
                .createdBy(userId)
                .build();
        return toResponse(eventRepository.save(event));
    }

    @CacheEvict(value = CacheConfig.EVENTS_CACHE, allEntries = true)
    public EventResponse updateEvent(String id, UpdateEventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));
        if (request.getTitle() != null) { event.setTitle(request.getTitle()); event.setSlug(SlugUtil.toSlug(request.getTitle())); }
        if (request.getDescription() != null) event.setDescription(request.getDescription());
        if (request.getShortDescription() != null) event.setShortDescription(request.getShortDescription());
        if (request.getType() != null) event.setType(request.getType());
        if (request.getStatus() != null) event.setStatus(request.getStatus());
        if (request.getStartDate() != null) event.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) event.setEndDate(request.getEndDate());
        if (request.getVenue() != null) event.setVenue(request.getVenue());
        if (request.getPricing() != null) event.setPricing(request.getPricing());
        if (request.getMaxCapacity() > 0) event.setMaxCapacity(request.getMaxCapacity());
        if (request.getCoverImageUrl() != null) event.setCoverImageUrl(request.getCoverImageUrl());
        if (request.getTags() != null) event.setTags(request.getTags());
        event.setFeatured(request.isFeatured());
        return toResponse(eventRepository.save(event));
    }

    @CacheEvict(value = CacheConfig.EVENTS_CACHE, allEntries = true)
    public void deleteEvent(String id) {
        if (!eventRepository.existsById(id)) throw new ResourceNotFoundException("Event", id);
        eventRepository.deleteById(id);
    }

    public EventRegistration registerForEvent(String eventId, String userId, EventRegistrationRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", eventId));
        if (event.getStatus() != EventStatus.PUBLISHED)
            throw new BadRequestException("Event is not open for registration");
        if (event.getMaxCapacity() > 0 && event.getRegisteredCount() >= event.getMaxCapacity())
            throw new BadRequestException("Event is full");
        if (registrationRepository.findByEventIdAndUserId(eventId, userId).isPresent())
            throw new BadRequestException("Already registered for this event");

        EventRegistration reg = EventRegistration.builder()
                .eventId(eventId).userId(userId)
                .attendeeCount(request.getAttendeeCount())
                .notes(request.getNotes()).status("CONFIRMED").build();
        event.setRegisteredCount(event.getRegisteredCount() + request.getAttendeeCount());
        eventRepository.save(event);
        return registrationRepository.save(reg);
    }

    private EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId()).title(event.getTitle()).slug(event.getSlug())
                .description(event.getDescription()).shortDescription(event.getShortDescription())
                .type(event.getType()).status(event.getStatus())
                .startDate(event.getStartDate()).endDate(event.getEndDate())
                .venue(event.getVenue()).pricing(event.getPricing())
                .maxCapacity(event.getMaxCapacity()).registeredCount(event.getRegisteredCount())
                .coverImageUrl(event.getCoverImageUrl()).imageUrls(event.getImageUrls())
                .tags(event.getTags()).featured(event.isFeatured())
                .createdAt(event.getCreatedAt()).build();
    }
}
