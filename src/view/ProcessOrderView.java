package view;

import controller.OrderController;
import controller.ReceiptController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Receipt;

public class ProcessOrderView extends Stage{

	private VBox root = new VBox();
	private Scene scene = new Scene(root, 375, 375);
	private Label title = new Label("Process Payment");
	private Button pay = new Button("Pay");
	private TextField orderID = new TextField();
	private TextField paymentType = new TextField();
	private TextField paymentAmount = new TextField();
	
	public ProcessOrderView(Integer orderId) {
		
		VBox.setMargin(root, new Insets(25));
		
		GridPane form = processOrderForm();
		GridPane.setMargin(form, new Insets(25));
		
		orderID.setText(String.valueOf(orderId));
		
		pay.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Integer orderIdReceipt = orderId;
				Double receiptOrderAmount = Double.parseDouble(paymentAmount.getText());
				String paymentype = paymentType.getText();
				
				Receipt receipt = new Receipt(orderIdReceipt, receiptOrderAmount, paymentype);
				ReceiptController rc = new ReceiptController();
				rc.makeReceipt(receipt);
				
				OrderController oc = new OrderController();
				oc.updateStatusToPaid(orderId);
				
				RecieptView rv = new RecieptView();
				
				rv.show();
				Close();
			}
		});
		
		root.setPadding(new Insets(25));
		
		title.setPadding(new Insets(25));
		
		pay.setMaxWidth(75);
		
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		form.setAlignment(Pos.TOP_CENTER);
		
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(title, form);
		
		this.setTitle("MysticGrill - Process Payment");
		this.setScene(scene);
	}
	
	public GridPane processOrderForm() {
		
		GridPane form = new GridPane();
		form.setVgap(15);
		form.setHgap(15);
		
		orderID.setEditable(false);
		orderID.setStyle("-fx-opacity: 0.4; -fx-background-color: #ababab; -fx-border-color: #818080; -fx-border-radius: 5;");
		
		form.add(new Label("Order ID : "), 0, 0);
		form.add(orderID, 1, 0);
		form.add(new Label("Payment Type : "), 0, 1);
		form.add(paymentType, 1, 1);
		form.add(new Label("Payment Amount : "), 0, 2);
		form.add(paymentAmount, 1, 2);
		form.add(pay, 1, 3);
		
		return form;
		
	}
	
	public void Close() {
		this.close();
	}
	
}