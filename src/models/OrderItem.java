package models;

public class OrderItem {
	
	private Integer orderItemId;
	private Integer orderId;
	private MenuItems menuItem;
	private Integer quantity;
	
	public OrderItem(Integer orderId, MenuItems menuItem, Integer quantity) {
		super();
		this.orderId = orderId;
		this.menuItem = menuItem;
		this.quantity = quantity;
	}
	
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public MenuItems getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItems menuItem) {
		this.menuItem = menuItem;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	

	
	
	
	

}
