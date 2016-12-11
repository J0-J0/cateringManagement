package com.jojo.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 食品信息
 * 
 * @author flash.J
 *
 */
public class Food {

	private int foodId;
	private String foodName;
	private double foodPrice;
	private String foodType;
	private String description; // 描述
	private int num; // 数量
	private Date addTime;
	private String addTime_;
	private int merchantId;
	private List<String> pics;

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
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

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
	
	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

}
