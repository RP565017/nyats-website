package org.nyalbanytamilsangam.api.event;

import org.junit.jupiter.api.Test;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.event.controller.EventController;
import org.nyalbanytamilsangam.api.event.dto.EventResponse;
import org.nyalbanytamilsangam.api.event.model.EventStatus;
import org.nyalbanytamilsangam.api.event.model.EventType;
import org.nyalbanytamilsangam.api.event.service.EventService;
import org.nyalbanytamilsangam.api.security.CustomUserDetailsService;
import org.nyalbanytamilsangam.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser
    void getEvents_shouldReturn200() throws Exception {
        EventResponse event = EventResponse.builder()
                .id("1").title("Test Event").slug("test-event")
                .type(EventType.CULTURAL).status(EventStatus.PUBLISHED)
                .startDate(Instant.now()).build();

        PagedResponse<EventResponse> pagedResponse = PagedResponse.<EventResponse>builder()
                .content(List.of(event)).totalElements(1).totalPages(1).number(0).size(10).build();

        when(eventService.getPublishedEvents(0, 10, null, null)).thenReturn(pagedResponse);

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].title").value("Test Event"));
    }
}
