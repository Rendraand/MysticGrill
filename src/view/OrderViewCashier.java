package view;

import java.sql.Date;
import java.util.ArrayList;

import javax.crypto.Cipher;

import controller.OrderController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.MenuItems;
import models.Order;

public class OrderViewCashier extends Stage {
	
	private OrderController orderController = new OrderController();
	private VBox root = new VBox();
	private Scene scene = new Scene(root, 700, 600);
	private TableView<Order> table;
	private Label title = new Label("List Order");
	private Label subTitle = new Label("Select a column to process an order");
	private Button processOrder = new Button("Process Order");
	private Button viewReceipts = new Button("View Receipts");
	
	public OrderViewCashier() {
		
		table = createOrderList();
		LoadServedOrder();
		
		VBox.setMargin(table, new Insets(25));
		
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		subTitle.setStyle("-fx-font-style: italic;");
		
		HBox root2 = new HBox();
		
		root.setPadding(new Insets(15));
		root.setAlignment(Pos.TOP_CENTER);
		
		root2.getChildren().addAll(viewReceipts, processOrder);
		root2.setAlignment(Pos.TOP_RIGHT);
		root2.setMargin(processOrder, new Insets(0, 25, 0, 25));
		
		processOrder.setAlignment(Pos.TOP_RIGHT);
		processOrder.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					
				Order selectedOrder = table.getSelectionModel().getSelectedItem();
				Integer orderId = selectedOrder.getOrderId();
				
					if(selectedOrder != null) { 
						ProcessOrderView pov = new ProcessOrderView(orderId);
						pov.show();
						Close();
					}
					
				} catch (Exception e) {
					
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("No order has been selected");
					alert.setHeaderText(null);
					alert.setContentText("Please Select the Order first");
					alert.showAndWait();
				}
			}
		});
		
		viewReceipts.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				RecieptView rv = new RecieptView();
				rv.show();
				Close();
			}
		});
		
		root.getChildren().addAll(title, subTitle, table, root2);
		
		this.setTitle("MysticGrill - Process Order [Cashier]");
		this.setScene(scene);
		
	}

	
	private TableView<Order> createOrderList(){
		
		TableView<Order> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		
		TableColumn<Order, Integer> userIdColumn = new TableColumn<>("User ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		
		TableColumn<Order, String> orderStatusColumn = new TableColumn<>("Order Status");
		orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
		
		TableColumn<Order, Date> orderDate = new TableColumn<>("Order Date");
		orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		
		TableColumn<Order, Double> orderTotalColumn = new TableColumn<>("Total Price");
		orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
		
		table.getColumns().add(orderIdColumn);
		table.getColumns().add(userIdColumn);
		table.getColumns().add(orderStatusColumn);
		table.getColumns().add(orderDate);
		table.getColumns().add(orderTotalColumn);	
		
		return table;
		
	}
	
	public void LoadServedOrder() {
		
		ArrayList<Order> data = orderController.getAllServedOrderList();
		
		table.getItems().setAll(data);
		
	}
	
	public void Close() {
		this.close();
	}
	
}