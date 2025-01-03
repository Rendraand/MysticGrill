package models;

import java.sql.Date;

public class Order {
	
	private Integer orderId;
	private Integer userId;
	private String orderStatus;
	private Date orderDate;
	private Double orderTotal;
	

	public Order(Integer userId, String orderStatus, Double orderTotal) {
		super();
		this.userId = userId;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
	}
	
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}
	
	
	
	
	
	
	
	
}
