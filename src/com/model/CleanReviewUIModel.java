package com.model;


public class CleanReviewUIModel {
	
private int cleanId;
	
	public int getCleanId() {
		return cleanId;
	}

	public void setCleanId(int cleanId) {
		this.cleanId = cleanId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}


	
	public void setCleanReviewDetails(String cleanReviewDetails) {
		this.cleanReviewDetails = cleanReviewDetails;
	}

	public String getCleanReviewDetails() {
		return cleanReviewDetails;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}


	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getProductType() {
		return productType;
	}


	private int reviewId;
	private String cleanReviewDetails;
	
	private int productId;
	
	private int productType;

}
