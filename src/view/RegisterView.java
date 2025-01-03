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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;


public class RegisterView extends Stage {

	private GridPane rootRegister = new GridPane();
	private Scene scene = new Scene(rootRegister, 500, 400);
	private Label titleLabel;
	private UserController userController = new UserController();
	
	
	Button loginButton = new Button("Login here if you have an account");
	
	// form
	Label usernameLabel = new Label();
	TextField usernameInput = new TextField();
	
	Label emailLabel = new Label();
	TextField emailInput = new TextField();
	
	Label passwordLabel= new Label();
	PasswordField passwordInput = new PasswordField();
	
	Label confirmPassLabel = new Label();
	PasswordField confirmPasswordInput = new PasswordField();
	
	Button registerButton = new Button("Register");
	

	public void getViewRegister() {
			
		BorderPane registerPage = new BorderPane();
		GridPane registerForm = new GridPane();
		
		titleLabel = new Label();
		titleLabel.setText("REGISTER FORM");
		titleLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold;");
		
		
		usernameLabel.setText("Username: ");
		emailLabel.setText("Email: ");
		passwordLabel.setText("Password: ");
		confirmPassLabel.setText("Confirm Password: ");
		
		registerForm.add(usernameLabel, 0, 0);
		registerForm.add(usernameInput, 1, 0);
		registerForm.add(emailLabel, 0, 1);
		registerForm.add(emailInput, 1, 1);
		registerForm.add(passwordLabel, 0, 2);
		registerForm.add(passwordInput, 1, 2);
		registerForm.add(confirmPassLabel, 0, 3);
		registerForm.add(confirmPasswordInput, 1, 3);
		registerForm.add(registerButton, 1, 4);

		
		
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					String name = usernameInput.getText();
					String email = emailInput.getText();
					String pass = passwordInput.getText();
					String confirmPass = confirmPasswordInput.getText();
					String role ="Customer";
					
					if((!email.contains("@"))&&(!email.contains("."))){
						emailInput.clear();
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Wrong email input");
						alert.setHeaderText(null);
						alert.setContentText("Email is not valid");
						alert.showAndWait();
					}else if(pass.length()< 6) {
						passwordInput.clear();
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Wrong password input");
						alert.setHeaderText(null);
						alert.setContentText("Password must have at least 6 characters");
						alert.showAndWait();
					}else if(!pass.equals(confirmPass)) {
						confirmPasswordInput.clear();
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Wrong password confirmation");
						alert.setHeaderText(null);
						alert.setContentText("Password is not the same");
						alert.showAndWait();
					}else{
						usernameInput.clear();
						emailInput.clear();
						passwordInput.clear();
						confirmPasswordInput.clear();
						User user = new User(role, name, email, pass);
						userController.insertUser(user);
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Register Success");
						alert.setHeaderText(null);
						alert.setContentText("Register Sucessfull");
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
		
		
		registerForm.add(ButtonGoToLogin(), 1, 5);
		
		registerForm.setVgap(20);
		registerForm.setHgap(10);
		
		registerPage.setTop(titleLabel);
		registerPage.setMargin(titleLabel, new Insets(10));
		
		registerPage.setCenter(registerForm);
		registerPage.setMargin(registerForm, new Insets(20));
		
		rootRegister.getChildren().clear();
		rootRegister.getChildren().addAll(registerPage);
		
//		return registerPage;
	}
	
//	public Scene getRegisterScene() {
//		getViewRegister();
//		return scene;
//	}
	public RegisterView() {
		getViewRegister();
		this.setTitle("MysticGrill Register");
		rootRegister.setAlignment(Pos.CENTER);
		this.setScene(scene);
	}
	
	public Button ButtonGoToLogin() {
		loginButton.setOnAction(e->{
			this.close();
			rootRegister.getChildren().clear();
			new LoginView().show();
		});
		return loginButton;
	}
}
	