package com.jojo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ê³Æ·ÆÀÂÛ
 * @author flash.J
 *
 */
public class FoodComment {

	private int foodCommentId;
	private String comment;
	private int userId;
	private int foodId;
	private Date addTime;
	private String addTime_;

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
	
	public String getAddTime_() {
		return addTime_;
	}

	public String setAddTime_() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		addTime_ = format.format(this.addTime);
		return addTime_;
	}
}
