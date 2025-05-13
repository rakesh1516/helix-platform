package com.helix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.helix.alert.AlertClassifierClient;
import java.util.Map;

@RestController
public class AlertController {

    @Autowired
    private AlertClassifierClient alertClassifierClient;

    @PostMapping("/classify-alert")
    public ResponseEntity<Map<String, String>> classifyAlert(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        Map<String, String> result = alertClassifierClient.classify(message);
        return ResponseEntity.ok(result);
    }
}
