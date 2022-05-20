package com.Hexaware.CMS.Model;


public class OrderDetails {
	private String orderNo;
	private String vendorId;
	private String userId;
	private String foodId;
	private int quantity;
	private String orderTime;
	private double orderValue;
	private String orderStatus;
	private double orderRating;
	public OrderDetails() {
		//default constructor
	}
	
	public OrderDetails(String orderNo, String vendorId, String userId, String foodId, int quantity,
			String timestamp, double orderValue, String orderStatus, double orderRating) {
		super();
		this.orderNo = orderNo;
		this.vendorId = vendorId;
		this.userId = userId;
		this.foodId = foodId;
		this.quantity = quantity;
		this.orderTime = timestamp;
		this.orderValue = orderValue;
		this.orderStatus = orderStatus;
		this.orderRating = orderRating;
	}

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(double orderValue) {
		this.orderValue = orderValue;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderRating() {
		return orderRating;
	}

	public void setOrderRating(double orderRating) {
		this.orderRating = orderRating;
	}
	
	
	
	
	
}
