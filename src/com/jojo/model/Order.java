package com.jojo.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * order,想在里面加个list<food>
 * 
 * @author flash.J
 *
 */
public class Order {

	private int orderId;
	private int userId;
	private int userIdCard;
	private int merchantId;
	private String merchantName;
	private int status; // 未发货，发货，收货
	private double sum;
	private String way;
	private String address;
	private Date addTime;
	private String addTime_;
	private Date ackTime;
	private String ackTime_;
	private List<OrderFood> foodList = new ArrayList<OrderFood>();

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public int getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(int userIdCard) {
		this.userIdCard = userIdCard;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public List<OrderFood> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<OrderFood> foodList) {
		this.foodList = foodList;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getAckTime() {
		return ackTime;
	}

	public void setAckTime(Date ackTime) {
		this.ackTime = ackTime;
	}

	public String setAddTime_() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		addTime_ = format.format(this.addTime);
		return addTime_;
	}

	public String setAckTime_() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ackTime_ = format.format(this.ackTime);
		return ackTime_;
	}

	public String getAddTime_() {
		return addTime_;
	}

	public String getAckTime_() {
		return ackTime_;
	}
}
