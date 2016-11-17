package com.jojo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �û���
 * 
 * @author flash.J
 *
 */
public class User {
	private int userId;
	private String userName;
	private String password;
	private int userIdCard; // ѧ��
	private String userRealName; // ��ʵ����
	private String sex;
	private int age;
	private String userTel; // �绰
	private String address; // �ͻ���ַ����һ��Ҫ
	private int group; // ��Ա�ȼ�
	private Date addTime;
	private String addTime_;
	private double sum; // �ܼ�����
	private String pic;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(int userIdCard) {
		this.userIdCard = userIdCard;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
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

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
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

}
