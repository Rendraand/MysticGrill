package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import models.DbConnection;
import models.Order;
import models.Receipt;

public class ReceiptController {
	
	public PreparedStatement getConnection(String query) throws SQLException {
		Connection connection = DbConnection.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(query);
		return ps;
	}
	
	public void makeReceipt(Receipt receipt) {
		String query = "INSERT INTO Receipts (orderId, receiptPaymentAmount, receiptPaymentDate, receiptType)"
						+ "VALUES(?,?,CURRENT_DATE,?)";
		try(PreparedStatement ps = getConnection(query)){
			ps.setInt(1, receipt.getOrderId());
			ps.setDouble(2, receipt.getReceiptOrderAmount());
			ps.setString(3, receipt.getReceiptType());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Receipt> getAllReceiptList() {
		
		ArrayList<Receipt> OrderList = new ArrayList<>();
		
		String query = "SELECT * FROM Receipts";
		
		try (Connection connection = DbConnection.getInstance().getConnection();
				Statement statement = 	connection.createStatement();
				ResultSet resultSet = 	statement.executeQuery(query)) {
			
			while(resultSet.next()) {
				Integer receiptId = 	resultSet.getInt("receiptId");
				Integer orderId = 	resultSet.getInt("orderId");
				Double paymentAmount = 	resultSet.getDouble("receiptPaymentAmount");
				Date paymnetDate = 	resultSet.getDate("receiptPaymentDate");
				String receiptType = 	resultSet.getString("receiptType");
				
				Receipt receipt = new 	Receipt(orderId, paymentAmount, receiptType);
				
				receipt.setReceiptId(receiptId);
				receipt.setReceiptPaymentDate(paymnetDate);
				
//				order.setUserId(userId);
//				order.setOrderDate(orderDate);
				
				OrderList.add(receipt);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return OrderList;
	}
	
}