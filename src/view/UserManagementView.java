package view;

import java.util.ArrayList;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.MenuItems;
import models.User;

public class UserManagementView extends Stage{
	private VBox rootMenu = new VBox();
	private Scene scene = new Scene(rootMenu, 800, 500);
	UserController userController = new UserController();
	private ObservableList<User> user = FXCollections.observableArrayList();
	private TableView<User> table;
	private Boolean Flag;
	
	public UserManagementView(Boolean flag) {
		table = createProductTable();
		LoadUser();
		VBox.setMargin(table, new Insets(25));
		
		GridPane form = removeUserForm(table,flag);
		VBox.setMargin(form, new Insets(25));
		
		rootMenu.getChildren().addAll(table,form);
		
		this.setTitle("MysticGrill User Management");
		this.setScene(scene);
	}
	
	private String name;
	private String role;
	private String email;
	
	private GridPane removeUserForm(TableView<User> user, Boolean flags) {
		Flag = flags;
		
		GridPane form = new GridPane();
		form.setVgap(20);
		
		Button changeUserButton = new Button("Change User Role");
		Button removeUserButton = new Button("Remove User");
		
		TextField changeInput = new TextField();
		form.add(new Label("User Role: "), 0, 0);
		
		
		
		form.add(changeInput, 1, 0);
		form.setHgap(10);
		form.add(changeUserButton, 2, 0);
		form.add(removeUserButton, 3, 0);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				name = newSelection.getUserName();
				role = newSelection.getRole();
				email = newSelection.getUserEmail();
				
				changeInput.setText(role);
			}
		});
		
		changeUserButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				User selectedMenu = table.getSelectionModel().getSelectedItem();
				
				if(selectedMenu != null) {
					role = changeInput.getText();
					userController.updateUser(role,name, email);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Input order successful");
					alert.setHeaderText(null);
					alert.setContentText("Your order is input successfully");
					alert.showAndWait();
					LoadUser();
				}else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid User");
					alert.setHeaderText(null);
					alert.setContentText("User must be selected!");
					alert.showAndWait();
				}
				
			}
		});
		
		removeUserButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				User selectedMenu = table.getSelectionModel().getSelectedItem();
				
				if(selectedMenu != null) {
					userController.deleteUser(name, email);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Input order successful");
					alert.setHeaderText(null);
					alert.setContentText("Your order is input successfully");
					alert.showAndWait();
					LoadUser();
				}else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid User");
					alert.setHeaderText(null);
					alert.setContentText("User must be selected!");
					alert.showAndWait();
				}
			}
		});
		
		return form;
	}
	
	private TableView<User> createProductTable() {
	 	
        TableView<User> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//       
//        TableColumn<User, Integer> idColumn = new TableColumn<>("User Id");
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> nameColumn = new TableColumn<>("User Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        
        TableColumn<User, String> roleColumn = new TableColumn<>("User Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, String> emailColumn = new TableColumn<>("User Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        
//        
//        TableColumn<User, String> passColumn = new TableColumn<>("User Passw");
//        passColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        
        table.getColumns().addAll(nameColumn,roleColumn,emailColumn);
        
        return table;
	}
	private void LoadUser() {
		ArrayList<User> data = userController.getAllUser();
	    table.getItems().setAll(data);
	}
	private void closeMenu() {
		this.close();
	}
}