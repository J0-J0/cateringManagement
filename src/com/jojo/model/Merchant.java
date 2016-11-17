package com.jojo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 小店老板
 * 
 * @author flash.J
 *
 */
public class Merchant {

	private int merchantId;
	private String merchantName;
	private String password;
	private int merchantIdCard; // 身份证
	private String merchantRealName; // 真实姓名
	private String sex;
	private int age;
	private String merchantTel; // 联系电话
	private Date addTime;
	private String addTime_;
	private double sum;
	private String pic;
	private String description;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMerchantIdCard() {
		return merchantIdCard;
	}

	public void setMerchantIdCard(int merchantIdCard) {
		this.merchantIdCard = merchantIdCard;
	}

	public String getMerchantRealName() {
		return merchantRealName;
	}

	public void setMerchantRealName(String merchantRealName) {
		this.merchantRealName = merchantRealName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMerchantTel() {
		return merchantTel;
	}

	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
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

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Merchant [merchantId=" + merchantId + ", merchantName=" + merchantName + ", password=" + password
				+ ", merchantIdCard=" + merchantIdCard + ", merchantRealName=" + merchantRealName + ", sex=" + sex
				+ ", age=" + age + ", merchantTel=" + merchantTel + ", addTime=" + addTime + ", sum=" + sum + "]";
	}

}
