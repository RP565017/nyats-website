package org.nyalbanytamilsangam.api.payment.controller;

import lombok.RequiredArgsConstructor;
import org.nyalbanytamilsangam.api.common.dto.ApiResponse;
import org.nyalbanytamilsangam.api.common.dto.PagedResponse;
import org.nyalbanytamilsangam.api.payment.model.Payment;
import org.nyalbanytamilsangam.api.payment.service.PaymentService;
import org.nyalbanytamilsangam.api.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-intent")
    public ResponseEntity<ApiResponse<Map<String, String>>> createIntent(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserPrincipal principal) {
        BigDecimal amount = new BigDecimal(body.get("amount").toString());
        String type = (String) body.getOrDefault("type", "MEMBERSHIP");
        String description = (String) body.getOrDefault("description", "NYATS Payment");
        return ResponseEntity.ok(ApiResponse.ok(
                paymentService.createPaymentIntent(principal.getId(), amount, type, description)));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String signature) {
        paymentService.handleWebhook(payload, signature);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PagedResponse<Payment>>> getMyPayments(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(paymentService.getMyPayments(principal.getId(), page, size)));
    }
}
