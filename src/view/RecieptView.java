package view;

import java.sql.Date;
import java.util.ArrayList;

import controller.MenuItemController;
import controller.ReceiptController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import models.MenuItems;
import models.Order;
import models.Receipt;

public class RecieptView extends Stage{
	
	private ReceiptController receiptController = new ReceiptController();
	private VBox root = new VBox();
	private Scene scene = new Scene(root, 800, 600);
	
	private Button back = new Button("Back");
	private TableView<Receipt> table;
	private Label title = new Label("Receipt LIST");
	
	public RecieptView() {
		
		table = receiptList();
		LoadReceiptList();
		
		VBox.setMargin(table, new Insets(25));
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(title, table, back);
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				OrderViewCashier ovc = new OrderViewCashier();
				ovc.show();
				Close();
			}
		});
		
		this.setScene(scene);
		this.setTitle("Mystic Grill - View Receipts");
		
	}
	
	public TableView<Receipt> receiptList(){
		
		TableView<Receipt> table = new TableView<>();
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn<Receipt, Integer> receiptIdColumn = new TableColumn<>("Receipt ID");
		receiptIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
		
		TableColumn<Receipt, Integer> orderIdColumn = new TableColumn<>("Order ID");
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		
		TableColumn<Receipt, Double> receiptOrderAmount = new TableColumn<>("Payment Amount");
		receiptOrderAmount.setCellValueFactory(new PropertyValueFactory<>("receiptOrderAmount"));
		
		TableColumn<Receipt, Date> date = new TableColumn<>("Payment Date");
		date.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentDate"));
		
		TableColumn<Receipt, String> receipType = new TableColumn<>("Payment TYPE");
		receipType.setCellValueFactory(new PropertyValueFactory<>("receiptType"));
		
		table.getColumns().addAll(receiptIdColumn, orderIdColumn, receiptOrderAmount, date, receipType);
		
		return table;
		
	}
	
	public void LoadReceiptList(){
		
		ArrayList<Receipt> data = receiptController.getAllReceiptList();
		table.getItems().setAll(data);
		
	}
	
	public void Close() {
		this.close();
	}
	
}