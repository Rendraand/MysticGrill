package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.DbConnection;
import models.MenuItems;

public class MenuManagementController {
	
	public PreparedStatement getConnection(String query) throws SQLException {
		Connection connection = DbConnection.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(query);
		return ps;
	}
	
	
	public boolean isMenuNameUnique(String menuName) {
		
		String query = "SELECT COUNT(*) AS total FROM MenuItems WHERE menuItemName = ?";
		
		try(PreparedStatement ps = getConnection(query)){
			
			ps.setString(1, menuName);;
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				int total =  rs.getInt("total");
//				if(total == 0) {
//					return true;
//				}
				return total == 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public void addMenu(MenuItems menuItems) {
		String query = "INSERT INTO MenuItems (menuItemName, menuItemDesc, menuItemPrice)" + " VALUES (?,?,?)";
		
		try(PreparedStatement ps = getConnection(query)){
			
			ps.setString(1, menuItems.getMenuItemName());
			ps.setString(2, menuItems.getMenuItemDesc());
			ps.setDouble(3, menuItems.getMenuItemPrice());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateMenu(MenuItems menuItems) {
		String query = "UPDATE MenuItems SET menuItemName = ?, menuItemDesc = ?, menuItemPrice = ? WHERE menuItemId = ?";
    	try (PreparedStatement ps = getConnection(query)){ 
    	  ps.setString(1, menuItems.getMenuItemName());
    	  ps.setString(2, menuItems.getMenuItemDesc());
    	  ps.setDouble(3, menuItems.getMenuItemPrice());
    	  ps.setInt(4, menuItems.getMenuItemId());
    	  
    	  ps.executeUpdate();
    	} catch (SQLException e) {
    	  e.printStackTrace();
    	}
	}
	
	
	public void deleteMenu(Integer menuItemId) {
		String query = "DELETE FROM MenuItems WHERE menuItemId = ?";
		
		try (PreparedStatement ps = getConnection(query)){
	        	ps.setInt(1, menuItemId);
	        	ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
}
