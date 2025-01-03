package view;

import java.util.ArrayList;
import java.util.Optional;

import controller.OrderController;
import controller.OrderItemController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.MenuItems;
import models.OrderItem;

public class OrderedMenuView extends Stage{
	
	private VBox root = new VBox();
	private Scene scene = new Scene(root, 800, 500);
	private TableView<OrderItem> table;
	private OrderItemController orderItemController = new OrderItemController();
	private OrderController orderController = new OrderController();
	private Label sumLabel = new Label();
	
	Button submitButton = new Button("Submit Order");
	Button backButton = new Button("Add more menu items");
	Button deleteButton = new Button("Delete menu item");
	Button updateQuantity = new Button("Change quantity");
	
	public OrderedMenuView(Integer userId, Integer orderId){
		table = createOrderedTable();
		LoadOrderItem(orderId);
		VBox.setMargin(table, new Insets(20));
		
		Double sum = calculateTotalSum(table);
		sumLabel.setText("Total Sum = " + String.format("%.3f", sum));
		sumLabel.setStyle("-fx-font-size: 16px; -fx-background-color: yellow; -fx-border-color:black; -fx-padding:5px;");
		
		submitButton = submitOrderButton(userId,orderId);
		backButton = backToMenuView(userId, orderId);
		
		GridPane buttonForm = new GridPane();
		buttonForm.setVgap(20);
		buttonForm.add(sumLabel,0,0);
		buttonForm.add(submitButton, 0, 1);
		buttonForm.setHgap(10);
		buttonForm.add(backButton, 1, 1);
		
		buttonForm.setPadding(new Insets(0,40,0,0));
		
		
		GridPane form = editOrderItem(table,orderId);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(buttonForm,form);
		VBox.setMargin(hbox, new Insets(25));
		
		root.getChildren().addAll(table,hbox);
		
		this.setTitle("MysticGrill Ordered Items");
		this.setScene(scene);
	}
	
	private TableView<OrderItem> createOrderedTable(){
		TableView<OrderItem> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
        TableColumn<OrderItem, String> menuItemName = new TableColumn<>("Menu Name");
//        menuItemName.setCellValueFactory(new PropertyValueFactory<>("MenuItem.setMenuItemName"));
        menuItemName.setCellValueFactory(cellData -> {
        	MenuItems menuItem = cellData.getValue().getMenuItem();
        	return new ReadOnlyStringWrapper(menuItem != null ? menuItem.getMenuItemName() : "");
        });
		
		TableColumn<OrderItem, Integer> orderQuantity = new TableColumn<>("Menu Quantity");
		orderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
	    TableColumn<OrderItem, Double> menuItemPrice = new TableColumn<>("Menu Price Per Quantity");
	    menuItemPrice.setCellValueFactory(cellData -> {
	        MenuItems menuItem = cellData.getValue().getMenuItem();
	        return new ReadOnlyObjectWrapper<>(menuItem != null ? menuItem.getMenuItemPrice() : null);
	    });
	    TableColumn<OrderItem, Double> totalAmount = new TableColumn<>("Total Price");
	    totalAmount.setCellValueFactory(cellData -> {
	        MenuItems menuItem = cellData.getValue().getMenuItem();
	        Integer quantity = cellData.getValue().getQuantity();
	        Double price = menuItem != null ? menuItem.getMenuItemPrice() : null;

	        if (quantity != null && price != null) {
	            return new ReadOnlyObjectWrapper<>(quantity * price);
	        } else {
	            return new ReadOnlyObjectWrapper<>(null);
	        }
	    });
		
		table.getColumns().addAll(menuItemName,orderQuantity, menuItemPrice,totalAmount);
		
		return table;
	}
	
	
	Integer OrderItemId;
	Integer Quantity = 0;
	private GridPane editOrderItem(TableView<OrderItem> orderItem, Integer orderId) {
		
		GridPane form = new GridPane();
		form.setVgap(20);
		
		Spinner<Integer> quantityInput = new Spinner<>(0,50,Quantity);
		Label editTitle = new Label();
		editTitle.setText("Ordered Item Edit");
		editTitle.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");
		
		form.add(editTitle, 0, 0);
		form.add(new Label("Menu Quantity: "), 0, 1);
		form.add(quantityInput, 1, 1);
		form.setHgap(10);
		form.add(deleteButton, 0, 2);
		form.add(updateQuantity, 1, 2);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				OrderItemId = newSelection.getOrderItemId();
				Quantity = newSelection.getQuantity();
				quantityInput.getValueFactory().setValue(Quantity);
			}
		});
		
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				OrderItem selectedMenu = table.getSelectionModel().getSelectedItem();
				
				if(selectedMenu != null) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Delete ordered menu confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Do you want to delete your ordered item?");
					Optional<ButtonType> result = alert.showAndWait();
			        if (result.isPresent() && result.get() == ButtonType.OK){
			        	
			        	orderItemController.deleteOrderItem(OrderItemId);
			        	LoadOrderItem(orderId);
			        } else {
			        }
				}else if(selectedMenu == null){
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Delete error");
					alert.setHeaderText(null);
					alert.setContentText("Ordered menu must be selected!");
					alert.showAndWait();
				}
			}
		});
		updateQuantity.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				OrderItem selectedMenu = table.getSelectionModel().getSelectedItem();
				
				if(selectedMenu != null) {
					orderItemController.updateOrderItemQuantity(OrderItemId, quantityInput.getValue());
					LoadOrderItem(orderId);
					
				}else if(selectedMenu == null){
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Update error");
					alert.setHeaderText(null);
					alert.setContentText("Ordered menu must be selected!");
					alert.showAndWait();
				}
				
			}
		});
		
		return form;
		
	}
	
	private void LoadOrderItem(Integer orderId) {
		ArrayList<OrderItem> data = orderItemController.getAllOrderById(orderId);
		table.getItems().setAll(data);
	}
	Double sum = 0.0;
	private double calculateTotalSum(TableView<OrderItem> table) {
		
	    for (OrderItem item : table.getItems()) {
	        MenuItems menuItem = item.getMenuItem();
	        Integer quantity = item.getQuantity();
	        Double price = menuItem != null ? menuItem.getMenuItemPrice() : null;

	        if (quantity != null && price != null) {
	            sum += quantity * price;
	        }
	    }
	    return sum;
	}
	private Button submitOrderButton(Integer userId, Integer orderId) {
		
		submitButton.setOnAction(e->{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Submit order confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Do you want to submit your order?");
			
			Optional<ButtonType> result = alert.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK){
	        	orderController.updatePrice(sum, orderId);
	            MenuItemsView mv = new MenuItemsView(userId,false,null);
	            mv.show();
	            
	            this.close();
	        } else {
	        }
		});
		return submitButton;
	}
	
	private Button backToMenuView(Integer userId, Integer orderId) {
		backButton.setOnAction(e ->{
			MenuItemsView mv = new MenuItemsView(userId,true, orderId);
			mv.show();
			this.close();
		});
		return backButton;
	}
	
}
