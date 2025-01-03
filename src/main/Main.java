package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.LoginView;
import view.MenuItemsView;
import view.RegisterView;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		RegisterView startScene = new RegisterView();
		startScene.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}