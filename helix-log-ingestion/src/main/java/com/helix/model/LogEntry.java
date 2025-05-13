package com.helix.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;
    private String serviceName;
    private String message;
    private LocalDateTime timestamp;
    private String classifiedAs;
    private String severity;
    private String recommendation;

    // ✅ GETTERS

    /**
	 * @return the recommendation
	 */
	public String getRecommendation() {
		return recommendation;
	}

	/**
	 * @param recommendation the recommendation to set
	 */
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public Long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getClassifiedAs() {
        return classifiedAs;
    }

    public String getSeverity() {
        return severity;
    }

    // ✅ SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setClassifiedAs(String classifiedAs) {
        this.classifiedAs = classifiedAs;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}

