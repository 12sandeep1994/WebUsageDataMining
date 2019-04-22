package com.model;

public class FrequencyVO {

	private int freqId;

	public int getFreqId() {
		return freqId;
	}

	public void setFreqId(int freqId) {
		this.freqId = freqId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}

	private String tokenName;

	private int freq;

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

	private String userId;

	private String query;

	private String url;

	private String hiddenUrl;

	private String title;

	private String desc;

}
