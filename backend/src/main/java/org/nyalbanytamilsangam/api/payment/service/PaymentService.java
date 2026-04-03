package org.nyalbanytamilsangam.api.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.common.exception.BadRequestException;
import org.nyalbanytamilsangam.api.payment.model.Payment;
import org.nyalbanytamilsangam.api.payment.model.PaymentStatus;
import org.nyalbanytamilsangam.api.payment.model.PaymentType;
import org.nyalbanytamilsangam.api.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Map<String, String> createPaymentIntent(String userId, BigDecimal amount, String type, String description) {
        try {
            PaymentIntent intent = PaymentIntent.create(PaymentIntentCreateParams.builder()
                    .setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("usd")
                    .setDescription(description)
                    .putMetadata("userId", userId)
                    .putMetadata("type", type)
                    .build());

            Payment payment = Payment.builder()
                    .userId(userId).type(PaymentType.valueOf(type))
                    .status(PaymentStatus.PENDING).amount(amount).currency("usd")
                    .stripePaymentIntentId(intent.getId())
                    .stripeClientSecret(intent.getClientSecret())
                    .description(description).build();
            paymentRepository.save(payment);

            return Map.of("clientSecret", intent.getClientSecret(), "paymentIntentId", intent.getId());
        } catch (StripeException e) {
            throw new BadRequestException("Payment processing failed: " + e.getMessage());
        }
    }

    public PagedResponse<Payment> getMyPayments(String userId, int page, int size) {
        Page<Payment> p = paymentRepository.findByUserId(userId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        return PagedResponse.<Payment>builder()
                .content(p.getContent()).totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages()).number(p.getNumber()).size(p.getSize())
                .first(p.isFirst()).last(p.isLast()).build();
    }

    public void handleWebhook(String payload, String signature) {
        // TODO: verify Stripe signature and update payment status
        log.info("Stripe webhook received");
    }
}
