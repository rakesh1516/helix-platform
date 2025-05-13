package com.helix.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.helix.ai.ClassificationResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlertClassifierService {

	@Value("${alert.classifier.service.url}")
    private String classifierUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ClassificationResult classify(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = new HashMap<>();
        payload.put("message", message);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<ClassificationResult> response =
                restTemplate.postForEntity(classifierUrl, request, ClassificationResult.class);
            return response.getBody();
        } catch (Exception e) {
            // Ideally log this
            return new ClassificationResult("Unknown", "Manual review required");
        }
    }
}