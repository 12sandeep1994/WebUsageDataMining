package com.model;

import java.io.Serializable;

public class ProductModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int productId;
		
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public void setFeatureVector(double featureVector) {
		this.featureVector = featureVector;
	}

	public double getFeatureVector() {
		return featureVector;
	}

	private String productName;
	
	private int productType;
	
	
	private double featureVector;
	
	

}
