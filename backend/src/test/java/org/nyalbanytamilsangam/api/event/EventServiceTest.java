package org.nyalbanytamilsangam.api.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.event.dto.CreateEventRequest;
import org.nyalbanytamilsangam.api.event.dto.EventResponse;
import org.nyalbanytamilsangam.api.event.model.Event;
import org.nyalbanytamilsangam.api.event.model.EventStatus;
import org.nyalbanytamilsangam.api.event.model.EventType;
import org.nyalbanytamilsangam.api.event.repository.EventRegistrationRepository;
import org.nyalbanytamilsangam.api.event.repository.EventRepository;
import org.nyalbanytamilsangam.api.event.service.EventService;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventRegistrationRepository registrationRepository;

    @InjectMocks
    private EventService eventService;

    private Event sampleEvent;

    @BeforeEach
    void setUp() {
        sampleEvent = Event.builder()
                .id("event1")
                .title("Pongal Celebration 2024")
                .slug("pongal-celebration-2024")
                .type(EventType.CULTURAL)
                .status(EventStatus.PUBLISHED)
                .startDate(Instant.now().plusSeconds(3600))
                .build();
    }

    @Test
    void createEvent_shouldReturnEventResponse() {
        CreateEventRequest request = new CreateEventRequest();
        request.setTitle("Pongal Celebration 2024");
        request.setType(EventType.CULTURAL);
        request.setStartDate(Instant.now().plusSeconds(3600));

        when(eventRepository.save(any(Event.class))).thenReturn(sampleEvent);

        EventResponse response = eventService.createEvent(request, "user1");

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Pongal Celebration 2024");
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void getEventBySlug_shouldReturnEvent() {
        when(eventRepository.findBySlug("pongal-celebration-2024")).thenReturn(Optional.of(sampleEvent));

        EventResponse response = eventService.getEventBySlug("pongal-celebration-2024");

        assertThat(response).isNotNull();
        assertThat(response.getSlug()).isEqualTo("pongal-celebration-2024");
    }

    @Test
    void getPublishedEvents_shouldReturnPagedResponse() {
        Page<Event> page = new PageImpl<>(List.of(sampleEvent), PageRequest.of(0, 10), 1);
        when(eventRepository.findByStatus(eq(EventStatus.PUBLISHED), any(Pageable.class))).thenReturn(page);

        PagedResponse<EventResponse> response = eventService.getPublishedEvents(0, 10, null, null);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getTotalElements()).isEqualTo(1);
    }
}
