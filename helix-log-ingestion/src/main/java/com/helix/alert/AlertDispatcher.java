package com.helix.alert;

import com.helix.model.AlertLog;
import com.helix.model.LogEntry;
import com.helix.repository.AlertLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class AlertDispatcher {

    @Autowired
    private AlertLogRepository alertRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${alert.email.to}")
    private String alertEmailTo;

    @Value("${alert.webhook.url}")
    private String alertWebhookUrl;

    public void send(LogEntry log) {
        System.out.println("🚨 ALERT TRIGGERED 🚨");
        System.out.println("Service: " + log.getServiceName());
        System.out.println("Severity: " + log.getSeverity());
        System.out.println("Message: " + log.getMessage());

        // Save to DB
        AlertLog alert = new AlertLog();
        alert.setServiceName(log.getServiceName());
        alert.setMessage(log.getMessage());
        alert.setSeverity(log.getSeverity());
        alert.setClassifiedAs(log.getClassifiedAs());
        alert.setTriggeredAt(LocalDateTime.now());
        alertRepo.save(alert);

        // Send email and webhook
        sendEmail(log);
        sendWebhook(log);
    }

    public void sendEmail(LogEntry log) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(alertEmailTo);
            message.setSubject("🚨 HELIX ALERT: " + log.getServiceName());
            message.setText("Severity: " + log.getSeverity() + "\nMessage: " + log.getMessage());
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }
    }

    public void sendWebhook(LogEntry log) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(alertWebhookUrl, log, String.class);
        } catch (Exception e) {
            System.out.println("Webhook send failed: " + e.getMessage());
        }
    }
}
