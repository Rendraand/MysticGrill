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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.Date;
import javafx.stage.Stage;
import models.MenuItems;
import models.Order;
import models.OrderItem;
import models.User;

public class PrepareView extends Stage {
	private VBox root = new VBox();
	private Scene scene = new Scene(root, 800, 500);
	OrderController orderController = new OrderController();
	private TableView<Order> table;
	
	
	public PrepareView() {
		table = createPrepareTable();
		LoadOrder();
		VBox.setMargin(table, new Insets(25));
		
		GridPane form = prepareForm(table);
		VBox.setMargin(form, new Insets(25));
		
		root.getChildren().addAll(table,form);
		
		this.setTitle("MysticGrill Prepare");
		this.setScene(scene);
	}
	
	private Integer orderId;
	private Integer userId;
	private String orderStatus;
	private Date orderDate;
	private Double orderTotal;
	
	private GridPane prepareForm(TableView<Order> order) {
		
		GridPane form = new GridPane();
		form.setVgap(20);
		
		Button prepareButton = new Button("Prepare");
		Button removeOrderButton = new Button("Remove Order");
		
		form.add(prepareButton, 1, 0);
		form.add(removeOrderButton, 2, 0);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				orderId = newSelection.getOrderId();
				userId = newSelection.getUserId();
				orderStatus = newSelection.getOrderStatus();
				orderDate = newSelection.getOrderDate();
				orderTotal = newSelection.getOrderTotal();
			}
		});
		
		prepareButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Order selectedMenu = table.getSelectionModel().getSelectedItem();
				
				if(selectedMenu != null) {
					orderController.updatePrepareOrder(orderId);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Update order successful");
					alert.setHeaderText(null);
					alert.setContentText("Your order has updated successfully");
					alert.showAndWait();
					LoadOrder();
				}else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Order");
					alert.setHeaderText(null);
					alert.setContentText("Order must be selected!");
					alert.showAndWait();
				}
				
			}
		});
		
		removeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Order selectedMenu = table.getSelectionModel().getSelectedItem();
				
				if(selectedMenu != null) {
					orderController.deleteOrder(orderId);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Input order successful");
					alert.setHeaderText(null);
					alert.setContentText("Your order is input successfully");
					alert.showAndWait();
					LoadOrder();
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
	
	private TableView<Order> createPrepareTable() {
	 	
        TableView<Order> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order Id");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Order, Integer> userIdColumn = new TableColumn<>("User Id");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Order, String> orderStatusColumn = new TableColumn<>("Order Status");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        TableColumn<Order, Double> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        
        TableColumn<Order, Double> orderTotalColumn = new TableColumn<>("Order Total");
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
        
        table.getColumns().addAll(orderIdColumn,userIdColumn,orderStatusColumn,orderDateColumn,orderTotalColumn);
        
        return table;
}
	
	private void LoadOrder() {
		ArrayList<Order> data = orderController.getAllPendingOrder();
	    table.getItems().setAll(data);
	}
}