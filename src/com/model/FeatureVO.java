package com.model;

public class FeatureVO {
	
	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	
	public double getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(double featureVector) {
		this.featureVector = featureVector;
	}

	public double getIdft() {
		return idft;
	}

	public void setIdft(double idft) {
		this.idft = idft;
	}

	
	
	public void setNoOfReviews(int noOfReviews) {
		this.noOfReviews = noOfReviews;
	}

	public int getNoOfReviews() {
		return noOfReviews;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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


	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}


	private int featureId;
	
	private String tokenName;
	
	private int freq;
	
	
	private double featureVector;
	
	private double idft;
	
	

	private int noOfReviews;
	
	private String userId;

	private String query;

	private String url;

	private String hiddenUrl;

	private String title;

	private String desc;


}
