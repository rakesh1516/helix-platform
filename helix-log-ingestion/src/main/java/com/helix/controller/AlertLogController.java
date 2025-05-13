package com.helix.controller;

import com.helix.model.AlertLog;
import com.helix.repository.AlertLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlertLogController {

    private final AlertLogRepository repository;

    public AlertLogController(AlertLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/alerts")
    public List<AlertLog> getAllAlerts() {
        return repository.findAll();
    }
}

