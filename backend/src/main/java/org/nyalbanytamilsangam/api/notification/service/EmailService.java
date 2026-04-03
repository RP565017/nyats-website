package org.nyalbanytamilsangam.api.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from:noreply@nyalbanytamilsangam.org}")
    private String fromEmail;

    @Async
    public void sendWelcomeEmail(String to, String firstName) {
        sendEmail(to, "Welcome to NY Albany Tamil Sangam!",
                "Dear " + firstName + ",\n\nWelcome to the NY Albany Tamil Sangam community!\n\nBest regards,\nNYATS Team");
    }

    @Async
    public void sendEventRegistrationConfirmation(String to, String firstName, String eventTitle) {
        sendEmail(to, "Event Registration Confirmed: " + eventTitle,
                "Dear " + firstName + ",\n\nYour registration for '" + eventTitle + "' has been confirmed.\n\nBest regards,\nNYATS Team");
    }

    @Async
    public void sendMembershipConfirmation(String to, String firstName, String membershipType) {
        sendEmail(to, "Membership Confirmed - NY Albany Tamil Sangam",
                "Dear " + firstName + ",\n\nYour " + membershipType + " membership has been activated.\n\nBest regards,\nNYATS Team");
    }

    @Async
    public void sendPasswordReset(String to, String resetLink) {
        sendEmail(to, "Password Reset - NY Albany Tamil Sangam",
                "Click the link to reset your password:\n\n" + resetLink + "\n\nThis link expires in 1 hour.\n\nNYATS Team");
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }
}
