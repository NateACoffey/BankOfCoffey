package io.github.Nateacoffey.Application;


import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;

public class AccountPageController implements Initializable {
	
	Statement st;
	String username;
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
	
	
	public void setStatement(Statement st) {
		this.st = st;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void getResultSet(ResultSet rs) throws SQLException {
		
		rs.next();
		
		user = new UserInformation(rs.getString("FIRST_NAME"),
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
		
		ArraySerial serial = new ArraySerial();
		serial.serial(UserInformation.ArrayOfAccounts, username, st);
		
	}
	
	//TODO switch scene back to log in and set user to null
	
	
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
