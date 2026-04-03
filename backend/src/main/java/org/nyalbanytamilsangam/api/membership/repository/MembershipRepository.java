package org.nyalbanytamilsangam.api.membership.repository;

import org.nyalbanytamilsangam.api.membership.model.Membership;
import org.nyalbanytamilsangam.api.membership.model.MembershipStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends MongoRepository<Membership, String> {
    Optional<Membership> findTopByUserIdOrderByCreatedAtDesc(String userId);
    List<Membership> findByUserId(String userId);
    Page<Membership> findByStatus(MembershipStatus status, Pageable pageable);
    long countByStatus(MembershipStatus status);
}
