package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.DbConnection;
import models.Order;

public class OrderController {
	
	public PreparedStatement getConnection(String query) throws SQLException {
		Connection connection = DbConnection.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(query);
		return ps;
	}
	public ArrayList<Order> getAllPendingOrder(){
		ArrayList<Order> orderList = new ArrayList<>();
		String query = "SELECT * FROM Orders WHERE orderStatus = 'Pending'";
		try(Connection connection = DbConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query)){
			
			while(resultSet.next()) {
				Integer userId = resultSet.getInt("userId");
				String orderStatus = resultSet.getString("orderStatus");
				Double orderTotal = resultSet.getDouble("orderTotal");
				
				Order order = new Order(userId, orderStatus, orderTotal);
				Integer orderId = resultSet.getInt("orderId");
				Date orderDate = resultSet.getDate("orderDate");
				
				order.setOrderId(orderId);
				order.setOrderDate(orderDate);
				orderList.add(order);
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return orderList;
	}
	public ArrayList<Order> getAllPrepareOrder(){
		ArrayList<Order> orderList = new ArrayList<>();
		String query = "SELECT * FROM Orders WHERE orderStatus = 'Prepare'";
		try(Connection connection = DbConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query)){
			
			while(resultSet.next()) {
				Integer userId = resultSet.getInt("userId");
				String orderStatus = resultSet.getString("orderStatus");
				Double orderTotal = resultSet.getDouble("orderTotal");
				
				Order order = new Order(userId, orderStatus, orderTotal);
				Integer orderId = resultSet.getInt("orderId");
				Date orderDate = resultSet.getDate("orderDate");
				
				order.setOrderId(orderId);
				order.setOrderDate(orderDate);
				orderList.add(order);
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return orderList;
	}
	
	public void insertOrder(Order order) {
		String query = "INSERT INTO Orders(userId, orderStatus, orderDate, orderTotal)"
						+"VALUES(?,?,CURRENT_DATE,?)";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, order.getUserId());
			ps.setString(2, order.getOrderStatus());
			ps.setDouble(3, order.getOrderTotal());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updatePrepareOrder(Integer orderId) {
		String query ="UPDATE Orders SET orderStatus = 'Prepare' WHERE orderId = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateServeOrder(Integer orderId) {
		String query ="UPDATE Orders SET orderStatus = 'Serve' WHERE orderId = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOrder(Integer orderId) {
		String query ="DELETE FROM Orders WHERE orderId = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Integer getLastId() {
		String query = "SELECT LAST_INSERT_ID() as last";
		Integer lastId = 0;
		try (Connection connection2 = DbConnection.getInstance().getConnection();
			Statement statement = connection2.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				lastId = rs.getInt("last");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		return lastId;
	}
	public void updatePrice(Double total, Integer orderId) {
		String query = "UPDATE Orders SET orderTotal = ? WHERE  orderId = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setDouble(1, total);
			ps.setInt(2,orderId);
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}
	public void updateStatusToPaid(Integer orderId) {
		
		String query = "UPDATE Orders\r\n"
				+ "SET orderStatus = ?"
				+ "WHERE orderId = ?";
		
		String orderStatus = "Paid";
		
		try(PreparedStatement ps = getConnection(query)){
			ps.setString(1, orderStatus);
			ps.setInt(2, orderId);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public ArrayList<Order> getAllServedOrderList() {
		
		ArrayList<Order> servedOrderList = new ArrayList<>();
		
		String query = "SELECT * FROM Orders WHERE orderStatus = 'Serve'";
		
		try (Connection connection = DbConnection.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			
			while(resultSet.next()) {
				Integer orderId = resultSet.getInt("orderId");
				Integer userId = resultSet.getInt("userId");
				String orderStatus = resultSet.getString("orderStatus");
				Date orderDate = resultSet.getDate("orderDate");
				Double orderTotal = resultSet.getDouble("orderTotal");
				
				Order order = new Order(userId, orderStatus, orderTotal);
				order.setOrderId(orderId);
				order.setOrderDate(orderDate);
				
				servedOrderList.add(order);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return servedOrderList;
	}
	
	
}
