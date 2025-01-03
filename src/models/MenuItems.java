package models;

public class MenuItems {
	private Integer menuItemId;
	private String menuItemName;
	private String menuItemDesc;
	private Double menuItemPrice;
	
	public MenuItems(Integer menuItemId, String menuItemName, String menuItemDesc, Double menuItemPrice) {
		super();
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.menuItemDesc = menuItemDesc;
		this.menuItemPrice = menuItemPrice;
	}

	public Integer getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Integer menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getMenuItemDesc() {
		return menuItemDesc;
	}

	public void setMenuItemDesc(String menuItemDesc) {
		this.menuItemDesc = menuItemDesc;
	}

	public Double getMenuItemPrice() {
		return menuItemPrice;
	}

	public void setMenuItemPrice(Double menuItemPrice) {
		this.menuItemPrice = menuItemPrice;
	}
	
	
	
	
	
	
	
	
}
	