package com.model;

public class PolarityModel {
	
	private int reviewId;
	
	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getPositiveRating() {
		return positiveRating;
	}

	public void setPositiveRating(int positiveRating) {
		this.positiveRating = positiveRating;
	}

	public int getNegativeRating() {
		return negativeRating;
	}

	public void setNegativeRating(int negativeRating) {
		this.negativeRating = negativeRating;
	}

	public int getNeutralRating() {
		return neutralRating;
	}

	public void setNeutralRating(int neutralRating) {
		this.neutralRating = neutralRating;
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


	private int positiveRating;
	
	private int negativeRating;
	
	private int neutralRating;
	
	private int productId;
	
	private int productType;
	
	

}
