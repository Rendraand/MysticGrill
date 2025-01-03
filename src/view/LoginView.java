package view;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends Stage{
	
	private GridPane rootRegister = new GridPane();
	private Scene scene = new Scene(rootRegister, 800, 500);
	private Label titleLabel;
	private UserController userController = new UserController();
	
	// form
	Label emailLabel = new Label();
	TextField emailInput = new TextField();
	
	Label passwordLabel = new Label();
	PasswordField passwordInput = new PasswordField();
	
	Button loginButton = new Button("Login");
	Button registerButton = new Button("Don't have account");
	
	public LoginView() {
		
		getViewLogin();
		this.setTitle("MysticGrill Login");
		rootRegister.setAlignment(Pos.CENTER);
		this.setScene(scene);
		
	}
	
	public void getViewLogin() {
		BorderPane loginPage = new BorderPane();
		GridPane loginForm = new GridPane();
		
		titleLabel = new Label();
		titleLabel.setText("LOGIN FORM");
		titleLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold;");
		
		emailLabel.setText("Email: ");
		passwordLabel.setText("Password: ");
		
		loginButton.setMaxWidth(getMaxWidth());
		registerButton.setMaxWidth(getMaxWidth());
	
		loginForm.add(emailLabel, 0, 0);
		loginForm.add(emailInput, 1, 0);
		loginForm.add(passwordLabel, 0, 1);
		loginForm.add(passwordInput, 1, 1);
		loginForm.add(loginButton, 1, 2);
	
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					String email = emailInput.getText();
					String pass = passwordInput.getText();
					Boolean check = userController.checkUser(email, pass);
					
					
					
					if(check.equals(true)) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Success user input");
						alert.setHeaderText(null);
						alert.setContentText("Login sucesss");
						alert.showAndWait();
						
						Integer id = userController.getId(email,pass);
						
						String role = userController.getRole(id);
						
						if(role.equals("Customer")) { //Aman
							
							MenuItemsView mv = new MenuItemsView(id,false,null); 
							mv.show();
			
						}else if(role.equals("Admin")) { //Aman
							UserManagementView umv = new UserManagementView(false);
							umv.show();
						
						}else if(role.equals("Chef")) {
							PrepareView pv = new PrepareView();
							pv.show();
							
						}else if(role.equals("Waiter")) {
							ServeView sv = new ServeView();
							sv.show();
						}else if(role.equals("Cashier")) {
							OrderViewCashier ovc = new OrderViewCashier();
							ovc.show();
						}
						closeLogin();
						
					}else if(check.equals(false)){
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Wrong user input");
						alert.setHeaderText(null);
						alert.setContentText("The email and password is wrong!");
						alert.showAndWait();
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
		
		loginForm.setVgap(20);
		loginForm.setHgap(10);
		
		loginPage.setTop(titleLabel);
		loginPage.setMargin(titleLabel, new Insets(10));
		
		loginPage.setCenter(loginForm);
		loginPage.setMargin(loginForm, new Insets(20));
		
		loginForm.add(ButtonGoToRegister(), 1, 3);
		
		rootRegister.getChildren().clear();
		rootRegister.getChildren().addAll(loginPage);
		
		// 3 parameter untuk scene >> layout, weight, height
	}
	public Button ButtonGoToRegister() {
		registerButton.setOnAction(e->{
			closeLogin();			
			RegisterView rv = new RegisterView();
			rv.show();
		});
		return registerButton;
	}
	public void closeLogin() {
		this.close();
	}
	
	
//	public Scene getLoginScene() {
//		Scene scene = new Scene(getViewLogin(), 400, 300);
//		return scene;
//	}
}
