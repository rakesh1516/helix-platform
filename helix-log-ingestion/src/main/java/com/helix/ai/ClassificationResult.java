package com.helix.ai;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ClassificationResult {
    private String classifiedAs;
    private String summary;
    private String recommendation;
    
	
    public ClassificationResult(String classifiedAs, String recommendation) {
        this.classifiedAs = classifiedAs;
        this.recommendation = recommendation;
    }
    
    /**
	 * @return the classifiedAs
	 */
	public String getClassifiedAs() {
		return classifiedAs;
	}


	/**
	 * @param classifiedAs the classifiedAs to set
	 */
	public void setClassifiedAs(String classifiedAs) {
		this.classifiedAs = classifiedAs;
	}


	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}


	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}


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




}