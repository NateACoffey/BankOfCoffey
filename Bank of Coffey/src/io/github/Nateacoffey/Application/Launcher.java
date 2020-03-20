package io.github.Nateacoffey.Application;


import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Launcher extends Application {
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		
        Parent root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
		primaryStage.setTitle("Bank of Coffey");
		primaryStage.getIcons().add(new Image("file:icon.png"));	//favicon
		primaryStage.setScene(new Scene(root, 640, 400));
		primaryStage.show();
        
        
    }
	
	
	public static void main(String[] args) {
		
		
		
		launch(args);
		
	}
}