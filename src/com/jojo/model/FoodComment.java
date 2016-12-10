package com.jojo.model;

import java.util.Date;

/**
 * Ê³Æ·ÆÀÂÛ
 * 
 * @author flash.J
 *
 */
public class FoodComment {

	private int foodCommentId;
	private String comment;
	private int userId;
	private String userName;
	private int foodId;
	private String foodName;
	private Date addTime;
	private int isPositive;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(int isPositive) {
		this.isPositive = isPositive;
	}

	public int getFoodCommentId() {
		return foodCommentId;
	}

	public void setFoodCommentId(int foodCommentId) {
		this.foodCommentId = foodCommentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
