package com.model;

import java.sql.Blob;

public class ReviewModelObj {

	private int reviewId;

	private int productId;

	private String reviewDetails;
	
	private int productType;

	
	
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getReviewId() {
		return reviewId;
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

	public void setReviewDetails(String reviewDetails) {
		this.reviewDetails = reviewDetails;
	}

	public String getReviewDetails() {
		return reviewDetails;
	}
	
	public void setReviewDetailsBlob(Blob reviewDetailsBlob) {
		this.reviewDetailsBlob = reviewDetailsBlob;
	}

	public Blob getReviewDetailsBlob() {
		return reviewDetailsBlob;
	}

	private Blob reviewDetailsBlob;
	
	
}
