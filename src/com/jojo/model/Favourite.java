package com.jojo.model;

public class Favourite {
	private int favouriteId;
	private int userId;
	private int foodId;
	private int merchantId;
	private String merchantName;
	private String foodName;
	private double foodPrice; // µ¥¼Û

	public int getFavouriteId() {
		return favouriteId;
	}

	public void setFavouriteId(int favouriteId) {
		this.favouriteId = favouriteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}
}
