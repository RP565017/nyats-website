package org.nyalbanytamilsangam.api.admin.service;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.announcement.repository.AnnouncementRepository;
import org.nyalbanytamilsangam.api.event.model.EventStatus;
import org.nyalbanytamilsangam.api.event.repository.EventRepository;
import org.nyalbanytamilsangam.api.membership.model.MembershipStatus;
import org.nyalbanytamilsangam.api.membership.repository.MembershipRepository;
import org.nyalbanytamilsangam.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final MembershipRepository membershipRepository;
    private final AnnouncementRepository announcementRepository;

    public Map<String, Object> getDashboardStats() {
        return Map.of(
                "totalUsers", userRepository.count(),
                "totalEvents", eventRepository.count(),
                "publishedEvents", eventRepository.findByStatus(EventStatus.PUBLISHED, org.springframework.data.domain.PageRequest.of(0, 1)).getTotalElements(),
                "activeMembers", membershipRepository.countByStatus(MembershipStatus.ACTIVE),
                "totalAnnouncements", announcementRepository.count()
        );
    }
}
