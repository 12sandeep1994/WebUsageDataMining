package com.model;

import java.io.Serializable;

public class SearchObj implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHiddenUrl() {
		return hiddenUrl;
	}

	public void setHiddenUrl(String hiddenUrl) {
		this.hiddenUrl = hiddenUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSearchId() {
		return searchId;
	}

	public void setSearchId(int searchId) {
		this.searchId = searchId;
	}

	public double getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(double featureVector) {
		this.featureVector = featureVector;
	}

	private String userId;
	
	private String query;
	
	private String url;
	
	private String hiddenUrl;
	
	private String title;
	
	private String desc;
	

	private int searchId;
	
	private double featureVector;

}
