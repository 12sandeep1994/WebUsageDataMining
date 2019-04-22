package com.model;

public class GoogleResult {

	private String url;

	private String title;

	private String desc;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	public String getHiddenUrl() {
		return hiddenUrl;
	}

	public void setHiddenUrl(String hiddenUrl) {
		this.hiddenUrl = hiddenUrl;
	}

	public double getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(double featureVector) {
		this.featureVector = featureVector;
	}

	private String hiddenUrl;
	

	private double featureVector;

}
