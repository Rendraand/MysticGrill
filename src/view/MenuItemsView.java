package view;

import java.util.ArrayList;

import controller.MenuItemController;
import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.MenuItems;
import models.Order;
import models.OrderItem;




public class MenuItemsView extends Stage {
	
	private VBox rootMenu = new VBox();
	private Scene scene = new Scene(rootMenu, 800, 500);
	private MenuItemController menuItemController = new MenuItemController();
	private OrderController orderController = new OrderController();
	private OrderItemController orderItemController = new OrderItemController();
	private ObservableList<MenuItems> menuItems = FXCollections.observableArrayList();
	private TableView<MenuItems> table;
//	private Boolean flag = false;
	private Integer orderId = null;
	private Boolean Flag;
	
	
	
	public MenuItemsView(Integer id,Boolean flag,Integer orderId) {
		
		table = createProductTable();
		LoadMenuItem();
		VBox.setMargin(table, new Insets(25));
		
		GridPane form = addMenuItemForm(table, id,flag,orderId);
		VBox.setMargin(form, new Insets(25));
		
		rootMenu.getChildren().addAll(table,form);
		
		this.setTitle("MysticGrill Menu Items");
		this.setScene(scene);
		
		
	}
	private Integer id;
	private String name;
	private String desc;
	private Double price;
	
	private GridPane addMenuItemForm(TableView<MenuItems> menu, Integer userId,Boolean flags,Integer ordId) {
		
		Flag = flags;
		
		GridPane form = new GridPane();
		form.setVgap(20);
		
		Button addOrderButton = new Button("Add Order");
		Button viewOrderedButton = new Button("View Ordered Menu");
		
//		TextField quantityInput = new TextField();
		Spinner<Integer> quantityInput = new Spinner<>(0,50,1);
		form.add(new Label("Menu Quantity: "), 0, 0);
		
		
		
		form.add(quantityInput, 1, 0);
		form.setHgap(10);
		form.add(addOrderButton, 0, 1);
		form.add(viewOrderedButton, 1, 1);
		
		
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				id = newSelection.getMenuItemId();
				name = newSelection.getMenuItemName();
				desc = newSelection.getMenuItemDesc();
				price = newSelection.getMenuItemPrice();
				
//				quantityInput.setText("");
			}
		});
		
		if(ordId != null) {
			orderId = ordId;
		}
		
		addOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				MenuItems selectedMenu = table.getSelectionModel().getSelectedItem();
				if(selectedMenu != null) {
					
					String status ="Pending";
					
					
					if(quantityInput.getValue().equals(null) && Flag == true) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Invalid quantity");
						alert.setHeaderText(null);
						alert.setContentText("Input quantity must not be filled!");
						alert.showAndWait();
					}else{
						try {
							Integer quantity = quantityInput.getValue();
//							System.out.println("Test");
							
							
							
							if(Flag == false) {
								Order order = new Order(userId,status,0.0);
								orderController.insertOrder(order);
								orderId = orderController.getLastId();
								
								MenuItems menuItems = menuItemController.getMenuById(id);
					
								OrderItem orderItem = new OrderItem(orderId,menuItems,quantity);
								orderItemController.insertOrderItem(orderItem);
								
								Flag = true;
							}else if(Flag == true) {
								
								MenuItems menuItems = menuItemController.getMenuById(id);
								
								OrderItem orderItem = new OrderItem(orderId,menuItems,quantity);
								orderItemController.insertOrderItem(orderItem);
							}
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Input order successful");
							alert.setHeaderText(null);
							alert.setContentText("Your order is input successfully");
							alert.showAndWait();
							
						} catch (NumberFormatException e) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Wrong quantity");
							alert.setHeaderText(null);
							alert.setContentText("Quantity input must be number");
							alert.showAndWait();
						}
					}
					
				}else if(selectedMenu == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Menu");
					alert.setHeaderText(null);
					alert.setContentText("Menu must be selected!");
					alert.showAndWait();
				}
				
			}
		});
		
		viewOrderedButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(orderId != null) {
					new OrderedMenuView(userId, orderId).show();
					closeMenu();
				}else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid order");
					alert.setHeaderText(null);
					alert.setContentText("No order yet!");
					alert.showAndWait();
				}
				
				
			}
		});
		
		
		return form;
		
		
	}
	
	private TableView<MenuItems> createProductTable() {
		 	
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
//	private void setupTableSelectionListener() {
//        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                name =(newSelection.getMenuItemName());
//                desc = (newSelection.getMenuItemDesc());
//                price = (newSelection.getMenuItemPrice());
//                
//                System.out.println(newSelection.getMenuItemId());
//				System.out.println(name);
//				System.out.println(desc);
//				System.out.println(price);
//            }
//        });
//    }
	
	
	private void LoadMenuItem() {
		ArrayList<MenuItems> data = menuItemController.getAllMenu();
	    table.getItems().setAll(data);
	}
	private void closeMenu() {
		this.close();
	}
	
	
}
