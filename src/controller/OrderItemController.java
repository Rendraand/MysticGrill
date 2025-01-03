package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.DbConnection;
import models.OrderItem;
import models.MenuItems;

public class OrderItemController {
	
	public PreparedStatement getConnection(String query) throws SQLException {
		Connection connection = DbConnection.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(query);
		return ps;
	}
	
	public void insertOrderItem(OrderItem orderItem) {
		String query = "INSERT INTO OrderItems(orderId, menuItemId, quantity)"
						+"VALUES(?,?,?)";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, orderItem.getOrderId());
			ps.setInt(2, orderItem.getMenuItem().getMenuItemId());
			ps.setInt(3, orderItem.getQuantity());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<OrderItem> getAllOrderById(Integer orderId){
		ArrayList<OrderItem> orderList = new ArrayList<>();
		String query = "SELECT * FROM orders JOIN orderitems \r\n"
				+ "ON orders.orderId=orderitems.orderId JOIN menuitems \r\n"
				+ "ON orderitems.menuItemId = menuitems.menuItemId\r\n"
				+ "WHERE orders.orderId = ?";
		try(PreparedStatement ps = getConnection(query)) {
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Integer ordId = orderId;
				
				Integer menuId= rs.getInt("menuItemId");
				String menuName = rs.getString("menuItemName");
				String menuDesc = rs.getString("menuItemDesc");
				Double menuItemPrice = rs.getDouble("menuItemPrice");
				
				Integer quantity = rs.getInt("quantity");
				
				MenuItems menuItem = new MenuItems(menuId,menuName,menuDesc,menuItemPrice);
				
				Integer orderItemId = rs.getInt("orderItemId");
				
				OrderItem orderItem = new OrderItem(ordId,menuItem,quantity);
				orderItem.setOrderItemId(orderItemId);
				
				orderList.add(orderItem);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
	public void deleteOrderItem(Integer orderItemId) {
		String query = "DELETE FROM OrderItems WHERE orderItemId = ?";
		try(PreparedStatement ps = getConnection(query)) {
			ps.setInt(1, orderItemId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrderItemQuantity(Integer orderItemId, Integer Quantity) {
		String query = "UPDATE OrderItems SET quantity = ? WHERE orderItemId = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, Quantity);
			ps.setInt(2, orderItemId);
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
