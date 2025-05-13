package com.helix.service;

import com.helix.model.LogEntry;
import com.helix.repository.LogEntryRepository;
import com.helix.specification.LogEntrySpecification;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;  // ✅ <-- THIS IS MISSING
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.helix.alert.AlertClassifierClient;
import com.helix.alert.AlertDispatcher;
import com.helix.alert.AlertRule;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LogEntryService {


	@Autowired
	private AlertClassifierClient classifierClient;

    @Autowired
    private AlertDispatcher alertDispatcher;

    private final LogEntryRepository repository;

    public LogEntryService(LogEntryRepository repository) {
        this.repository = repository;
    }

	/*
	 * public LogEntry save(LogEntry log) { LogEntry saved = repository.save(log);
	 * 
	 * if (AlertRule.shouldTrigger(saved)) { alertDispatcher.send(saved); } return
	 * saved; // return repository.save(log); }
	 */
    public LogEntry save(LogEntry log) {
        // Step 1: Enrich log with AI classification
        Map<String, String> result = classifierClient.classify(log.getMessage());
        log.setClassifiedAs(result.get("classifiedAs"));
        log.setRecommendation(result.get("recommendation"));

        // Step 2: Save to DB
        LogEntry saved = repository.save(log);

        // Step 3: Trigger alert if needed
        if (AlertRule.shouldTrigger(saved)) {
            alertDispatcher.send(saved);
        }

        return saved;
    }

    public List<LogEntry> getAll() {
    return repository.findAll();
    }

    public Page<LogEntry> search(String serviceName, String severity, String keyword,
                                 LocalDateTime startDate, LocalDateTime endDate,
                                 int page, int size) {

        Specification<LogEntry> spec = LogEntrySpecification.filterLogs(serviceName, severity, keyword, startDate, endDate);
        return repository.findAll(spec, PageRequest.of(page, size, Sort.by("timestamp").descending()));
    }
}
