package com.helix.specification;

import com.helix.model.LogEntry;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogEntrySpecification {

    public static Specification<LogEntry> filterLogs(
            String serviceName, String severity, String keyword,
            LocalDateTime startDate, LocalDateTime endDate) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (serviceName != null && !serviceName.isEmpty()) {
                predicates.add(cb.equal(root.get("serviceName"), serviceName));
            }

            if (severity != null && !severity.isEmpty()) {
                predicates.add(cb.equal(root.get("severity"), severity));
            }

            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("message")), "%" + keyword.toLowerCase() + "%"));
            }

            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("timestamp"), startDate, endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
