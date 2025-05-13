package com.helix.controller;

import com.helix.model.LogEntry;
import com.helix.service.LogEntryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LogController {

    private final LogEntryService service;

    public LogController(LogEntryService service) {
        this.service = service;
    }

    @PostMapping("/ingest")
    public LogEntry ingestLog(@RequestBody LogEntry log) {
        return service.save(log);
    }

    @GetMapping("/logs/all")
    public List<LogEntry> getAllLogs() {
    	return service.getAll();
     }

    @GetMapping("/logs/search")
    public Page<LogEntry> searchLogs(
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.search(serviceName, severity, keyword, startDate, endDate, page, size);
    }
}
