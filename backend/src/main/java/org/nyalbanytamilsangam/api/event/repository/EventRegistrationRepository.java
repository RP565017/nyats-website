package org.nyalbanytamilsangam.api.event.repository;

import org.nyalbanytamilsangam.api.event.model.EventRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends MongoRepository<EventRegistration, String> {
    List<EventRegistration> findByUserId(String userId);
    List<EventRegistration> findByEventId(String eventId);
    Optional<EventRegistration> findByEventIdAndUserId(String eventId, String userId);
    long countByEventId(String eventId);
}
