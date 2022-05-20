package com.Hexaware.CMS.Model;

public class Customer {
	private String userId;
	private String userName;
	private String userPhone;
	private String userEmail;
	private String coupon;
	private double userBalance;
	public Customer() {
		//default constructor
	}
	public Customer(String userId, String userName, String userPhone, String userEmail, String coupon,
			double userBalance) {
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.coupon = coupon;
		this.userBalance = userBalance;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public double getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}
	@Override
	public String toString() {
		return String.format("%-20s%-30s%-15s%-35s%-20s%s",
				userId, userName, userPhone, userEmail, coupon, userBalance);
	}
	
	
	
	
}
