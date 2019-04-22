package com.model;

public class ReviewModel {

	private int productid;

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getProducttype() {
		return producttype;
	}

	public void setProducttype(int producttype) {
		this.producttype = producttype;
	}

	public String getReviewDetails() {
		return reviewDetails;
	}

	public void setReviewDetails(String reviewDetails) {
		this.reviewDetails = reviewDetails;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrlType(String webUrlType) {
		this.webUrlType = webUrlType;
	}

	public String getWebUrlType() {
		return webUrlType;
	}

	private int producttype;

	private String reviewDetails;

	private String webUrl;

	private String webUrlType;
	
	

}
