package org.nyalbanytamilsangam.api.event.repository;

import org.nyalbanytamilsangam.api.event.model.Event;
import org.nyalbanytamilsangam.api.event.model.EventStatus;
import org.nyalbanytamilsangam.api.event.model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findBySlug(String slug);
    Page<Event> findByStatus(EventStatus status, Pageable pageable);
    Page<Event> findByStatusAndType(EventStatus status, EventType type, Pageable pageable);

    @Query("{'status': ?0, '$or': [{'title': {$regex: ?1, $options: 'i'}}, {'description': {$regex: ?1, $options: 'i'}}]}")
    Page<Event> searchByStatusAndKeyword(EventStatus status, String keyword, Pageable pageable);
}
