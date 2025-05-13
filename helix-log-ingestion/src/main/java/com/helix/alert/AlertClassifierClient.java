package com.helix.alert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlertClassifierClient {

    private static final Logger logger = LoggerFactory.getLogger(AlertClassifierClient.class);

    @Value("${alert.gpt.flask.url}")
    private String gptFlaskUrl;

    @Value("${alert.rule.fallback.url}")
    private String ruleFallbackUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> classify(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = new HashMap<>();
        payload.put("message", message);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        // 🔹 Try GPT Flask Classifier
        try {
            logger.info("Calling GPT Flask at: {}", gptFlaskUrl);
            logger.info("Payload: {}", payload);

            ResponseEntity<Map> response = restTemplate.postForEntity(gptFlaskUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("GPT response: {}", response.getBody());
                return response.getBody();
            }
        } catch (Exception e) {
            logger.error("GPT classifier failed: {}", e.getMessage());
        }

        // 🔹 Try Fallback Classifier
        try {
            logger.info("Calling fallback Flask at: {}", ruleFallbackUrl);
            logger.info("Payload: {}", payload);

            ResponseEntity<Map> fallback = restTemplate.postForEntity(ruleFallbackUrl, request, Map.class);

            if (fallback.getStatusCode() == HttpStatus.OK && fallback.getBody() != null) {
                logger.info("Fallback response: {}", fallback.getBody());
                return fallback.getBody();
            }
        } catch (Exception e) {
            logger.error("Fallback classifier failed: {}", e.getMessage());
        }

        // 🔹 Default fallback if both fail
        logger.warn("Both GPT and fallback failed. Returning 'Unknown' response.");

        Map<String, String> unknown = new HashMap<>();
        unknown.put("classifiedAs", "Unknown");
        unknown.put("recommendation", "Manual review needed");
        return unknown;
    }
}
