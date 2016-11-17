package com.jojo.model;

/**
 * 老实说，总觉得和cart重复
 * 
 * @author flash.J
 *
 */
public class OrderFood {

	private int orderFoodId;
	private int orderId;
	private int foodId;
	private String foodName;
	private double foodPrice;
	private int num;
	private double foodSum;

	public int getOrderFoodId() {
		return orderFoodId;
	}

	public void setOrderFoodId(int orderFoodId) {
		this.orderFoodId = orderFoodId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getFoodSum() {
		return foodSum;
	}

	public void setFoodSum(double foodSum) {
		this.foodSum = foodSum;
	}

}
