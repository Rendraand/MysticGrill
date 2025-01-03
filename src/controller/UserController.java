package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import models.DbConnection;
import models.User;

public class UserController {
	
	public PreparedStatement getConnection(String query) throws SQLException {
		Connection connection = DbConnection.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(query);
		return ps;
	}

	public void insertUser(User user) {
		String query ="INSERT INTO Users(userRole,userName,userEmail,userPassword)"
				+ "VALUES(?,?,?,?)";
		try(PreparedStatement ps = getConnection(query)){
			ps.setString(1, user.getRole());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getUserEmail());
			ps.setString(4, user.getUserPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getId(String email, String password) {
		String query ="Select userId FROM Users WHERE userEmail = ? AND userPassword = ? ";
		try(PreparedStatement ps = getConnection(query)) {
			ps.setString(1,email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			Integer id = 0;
			
			while(rs.next()) {
				id = rs.getInt("userId");
			}
			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean checkUser(String email, String password) {
		if(email.equals("") || password.equals("")){
			return false;
		}else {
			String query ="SELECT userEmail,userPassword FROM Users WHERE userEmail = ? AND userPassword = ?";
			try(PreparedStatement ps = getConnection(query)){
				
				ps.setString(1,email);
				ps.setString(2,password); 
				ResultSet rs = ps.executeQuery();
				
				String em ="";
				String pass ="";
				
				while (rs.next()) {
					em = rs.getString("userEmail");
	            	pass= rs.getString("userPassword");
	            }
				if (email.equals(em)&&password.equals(pass)) {
					return true;
				}else {
					return false;
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	public void updateUser(String role, String name, String email) {
		String query ="UPDATE Users SET userRole = ? WHERE userName = ? AND userEmail = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setString(1, role);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String name, String email) {
		String query ="DELETE FROM Users WHERE userName = ? AND userEmail = ?";
		try(PreparedStatement ps = getConnection(query)){
			ps.setString(1, name);
			ps.setString(2, email);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getAllUser(){
		ArrayList<User> userList = new ArrayList<>();
		String query = "SELECT * FROM users";
		try(Connection connection = DbConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query)){
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("userId");
				String role = resultSet.getString("userRole");
				String name = resultSet.getString("userName");
				String email = resultSet.getString("userEmail");
				String password = resultSet.getString("userPassword");
				
				User user = new User(role,name,email,password);
				userList.add(user);
				
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return userList;
	}
	
	public String getRole(Integer id) {
		String query ="Select userRole FROM Users WHERE userId = ?";
		try(PreparedStatement ps = getConnection(query)) {
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			String role = null;
			
			while(rs.next()) {
				role = rs.getString("userRole");
			}
			return role;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
