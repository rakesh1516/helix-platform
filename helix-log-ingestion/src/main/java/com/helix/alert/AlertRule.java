package com.helix.alert;

import com.helix.model.LogEntry;

public class AlertRule {

    public static boolean shouldTrigger(LogEntry log) {
        return "HIGH".equalsIgnoreCase(log.getSeverity()) ||
               "TimeoutError".equalsIgnoreCase(log.getClassifiedAs()) ||
               (log.getMessage() != null && log.getMessage().toLowerCase().contains("db"));
    }
}
