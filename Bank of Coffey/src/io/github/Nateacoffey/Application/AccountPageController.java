package io.github.Nateacoffey.Application;


import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AccountPageController implements Initializable {
	
	UserInformation user;
	
	public TextField test;
	public Text greeting;
	
	public Pane profile;
	public Text userName;
	public Text userAddress;
	public Text userCity;
	public Text userState;
	public Text userZipCode;
	public Text userPhoneNumber;
	
	
	public void getResultSet(ResultSet rs) throws SQLException {
		
		rs.next();
		
		user = new UserInformation(rs.getString("USERNAME"),
								   rs.getString("FIRST_NAME"),
									rs.getString("LAST_NAME"),
									 rs.getString("ADDRESS"),
									  rs.getString("CITY"),
									   rs.getString("STATE"),
									    rs.getInt("ZIP_CODE"),
									     rs.getString("PHONE_NUMBER"),
									      rs.getString("ACCOUNT_SERIAL"),
									       rs.getInt("AMOUNT_OF_ACCOUNTS")
									);
		
		greeting.setText("Hello, " + user.getFirstName());
		
		userName.setText(user.getFirstName() + " " + user.getLastName());
		userAddress.setText(user.getAddress());
		userCity.setText(user.getCity());
		userState.setText(user.getState());
		userZipCode.setText(Integer.toString(user.getZipCode()));
		userPhoneNumber.setText(user.getPhoneNumber());
		
	}
	
	public void toLogInScreen(ActionEvent e) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("LogInScreen.fxml"));
		Parent root = loader.load();
		
		Scene accountPageScene = new Scene(root);
		
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		user = null;
		
		window.setScene(accountPageScene);
		window.show();
		
		
	}
	
	
	public void showProfile(ActionEvent e) {
		profile.setVisible(true);
	}
	
	public void hideProfile(ActionEvent e) {
		profile.setVisible(false);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
	}
}
