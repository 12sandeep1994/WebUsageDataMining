package com.model;

import java.sql.Blob;

public class CleanReviewModel {
	
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

	
	public Blob getCleanReviewDetails() {
		return cleanReviewDetails;
	}

	public void setCleanReviewDetails(Blob cleanReviewDetails) {
		this.cleanReviewDetails = cleanReviewDetails;
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

	private int productId;
	
	private int productType;
	
	
	private Blob cleanReviewDetails;

}
