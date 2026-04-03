package org.nyalbanytamilsangam.api.payment.repository;

import org.nyalbanytamilsangam.api.payment.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Page<Payment> findByUserId(String userId, Pageable pageable);
    Optional<Payment> findByStripePaymentIntentId(String intentId);
}
