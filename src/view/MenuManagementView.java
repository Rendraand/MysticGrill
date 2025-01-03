package view;

import java.util.ArrayList;

import controller.MenuItemController;
import controller.MenuManagementController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import models.MenuItems;

public class MenuManagementView extends Stage{
	
	private VBox rootMenuManagement = new VBox();
	private Scene scene = new Scene(rootMenuManagement, 800, 500);
	private MenuItemController menuItemController = new MenuItemController();
	private ObservableList<MenuItems> menuItems = FXCollections.observableArrayList();
	private TableView<MenuItems> table;
	private MenuManagementController menuManagementController = new MenuManagementController();
	
	
	public MenuManagementView() {
		
		table = createMenuListTable();
		LoadMenuItem();
		
		VBox.setMargin(table, new Insets(25));
		
		GridPane form = menuManagementForm(table);
		VBox.setMargin(form, new Insets(25));
		
		rootMenuManagement.getChildren().addAll(table, form);
		
		this.setTitle("Mystic Grill - Menu Management Page");
		this.setScene(scene);
	}
	
	
	public TableView<MenuItems> createMenuListTable(){
		
		TableView<MenuItems> table = new TableView<>();
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
        TableColumn<MenuItems, Integer> idColumn = new TableColumn<>("Menu Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));

        TableColumn<MenuItems, String> nameColumn = new TableColumn<>("Menu Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));

        TableColumn<MenuItems, String> descColumn = new TableColumn<>("Menu Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemDesc"));

        TableColumn<MenuItems, Double> priceColumn = new TableColumn<>("Menu Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));
        
        table.getColumns().addAll(idColumn,nameColumn,descColumn,priceColumn);
		
		return table;
	}
	
	
	public GridPane menuManagementForm(TableView<MenuItems> table) 	{
		
		GridPane form = new GridPane();
		form.setVgap(20);
		form.setHgap(15);
		
		Label contentTitle = new Label("Menu Details");
		contentTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		TextField menuName = new TextField();
		TextField menuPrice = new TextField();
		TextField menuDesc = new TextField();
		
		Button addMenu = new Button("Add Menu");
		Button updateMenu = new Button("Update Menu");
		Button deleteMenu = new Button("Delete Menu");
		
		updateMenu.setMaxWidth(150);
		
		form.add(contentTitle, 1, 0);
		
		form.add(new Label("Menu Name : "), 0, 1);
		form.add(menuName, 1, 1);
		
		form.add(new Label("Menu Description : "), 0, 2);
		form.add(menuDesc, 1, 2);
		
		form.add(new Label("Menu Price : "), 0, 3);
		form.add(menuPrice, 1, 3);
		
		form.add(addMenu, 0, 4);
		form.add(updateMenu, 1, 4);
		form.add(deleteMenu, 4, 4);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				menuName.setText(newSelection.getMenuItemName());
				menuDesc.setText(newSelection.getMenuItemDesc());
				menuPrice.setText(String.valueOf(newSelection.getMenuItemPrice()));
			}
		});
		
		
		addMenu.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				try {
					
					String nameOfMenu = menuName.getText();
					String descOfMenu = menuDesc.getText();
					Double priceOfMenu = Double.parseDouble(menuPrice.getText());
					
					Boolean check = menuManagementController.isMenuNameUnique(nameOfMenu);
					
					if(nameOfMenu.equals("")) {
					
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Invalid input");
						alert.setHeaderText(null);
						alert.setContentText("Menu Name Must Be Unique");
						alert.showAndWait();
						
					} else if(check.equals(false)) {
						
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Invalid input");
						alert.setHeaderText(null);
						alert.setContentText("Menu Name Must Be Unique");
						alert.showAndWait();
						
					} else if(descOfMenu.length() < 10) {
							
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Wrong input");
						alert.setHeaderText(null);
						alert.setContentText("Menu Description Must be more than 10 characters");
						alert.showAndWait();
						
					} else if(priceOfMenu < 2.5){
			
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Wrong input");
						alert.setHeaderText(null);
						alert.setContentText("Menu Price greater than or equal to 2.5");
						alert.showAndWait();
						
					} else if(check.equals(true)) {
						
						MenuItems addedmenuItems = new MenuItems(null, nameOfMenu, descOfMenu, priceOfMenu);
						menuItems.add(addedmenuItems);
						menuManagementController.addMenu(addedmenuItems);
						LoadMenuItem();
						
						menuName.clear();
						menuDesc.clear();
						menuPrice.clear();
					}
					
				} catch (NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Wrong input");
					alert.setHeaderText(null);
					alert.setContentText("All form must be filled!");
					alert.showAndWait();
				}
				
			}
		});
		
		
		updateMenu.setOnAction(new EventHandler<ActionEvent>() {
			 
			@Override
			public void handle(ActionEvent event) {
				try {
					
					String updatedName = menuName.getText();
					String updatedDesc = menuDesc.getText();
					Double updatedPrice = Double.parseDouble(menuPrice.getText());
					
					MenuItems selectedMenu = table.getSelectionModel().getSelectedItem();
					Integer id = selectedMenu.getMenuItemId();
					
					if(selectedMenu != null) {
						MenuItems updatedMenuItems = new MenuItems(id, updatedName, updatedDesc, updatedPrice);
						menuManagementController.updateMenu(updatedMenuItems);
						menuItems.add(updatedMenuItems);
						LoadMenuItem();
						
						menuName.clear();
						menuDesc.clear();
						menuPrice.clear();
						
					}
					
				} catch (NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Wrong input");
					alert.setHeaderText(null);
					alert.setContentText("All form must be filled!");
					alert.showAndWait();
				}
			}
		});
		
		
		deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
		                
						MenuItems selectedMenu = table.getSelectionModel().getSelectedItem();
		                if (selectedMenu != null) {
		                    menuManagementController.deleteMenu(selectedMenu.getMenuItemId());  
		                    menuItems.remove(selectedMenu);  
		                    LoadMenuItem();
		                    
		                    menuName.clear();
							menuDesc.clear();
							menuPrice.clear();
		                }
			}
		});
		
		
		return form;
	}
	
	
	public void LoadMenuItem() {
		ArrayList<MenuItems> dataMenu = menuItemController.getAllMenu();
		
		table.getItems().setAll(dataMenu);
	}
}