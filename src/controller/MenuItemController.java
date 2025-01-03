package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.DbConnection;
import models.MenuItems;

public class MenuItemController {
	
	public PreparedStatement getConnection(String query) throws SQLException {
		Connection connection2 = DbConnection.getInstance().getConnection();
		PreparedStatement ps = connection2.prepareStatement(query);
		return ps;
	}

	public ArrayList<MenuItems> getAllMenu(){
		ArrayList<MenuItems> menuList = new ArrayList<>();
		String query = "SELECT * FROM menuitems";
		try(Connection connection = DbConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query)){
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("menuItemId");
				String name = resultSet.getString("menuItemName");
				String desc = resultSet.getString("menuItemDesc");
				Double price = resultSet.getDouble("menuItemPrice");
				
				MenuItems menuItems = new MenuItems(id,name,desc,price);
				menuList.add(menuItems);
				
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return menuList;
	}
	public MenuItems getMenuById(Integer menuId) {
		
		MenuItems menuItems = null;
		String query = "SELECT * FROM menuitems WHERE menuItemId = ?";
		try(PreparedStatement ps = getConnection(query)) {
			ps.setInt(1, menuId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Integer id = menuId;
				String name = rs.getString("menuItemName");
				String desc = rs.getString("menuItemDesc");
				Double price = rs.getDouble("menuItemPrice");
				
				menuItems = new MenuItems(id,name,desc,price);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuItems;
	}
	
}
