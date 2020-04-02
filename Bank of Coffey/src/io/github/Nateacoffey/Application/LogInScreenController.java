package io.github.Nateacoffey.Application;


import java.io.IOException;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;

import io.github.Nateacoffey.Accounts.AccountPageController;
import io.github.Nateacoffey.Admin.AdminPageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;


public class LogInScreenController implements Initializable {
	
	Statement st;
	ResultSet rs;
	Connection dbConnection = null;
	
	Hash hash = new Hash();
	
	private String usernameLocked;	//locks the username to prevent alteration
	
	public DialogPane newAccount;
	public TextField username;
	public PasswordField password;
	public Text userNotFound;
	
	public TextField newUsername;
	public TextField newPassword;
	public TextField newPasswordConfirm;
	public TextField newFirstName;
	public TextField newLastName;
	public TextField newAddress;
	public TextField newPhoneNumber;
	public TextField newCity;
	public TextField newZipCode;
	public Text duplicateUsername;
	public Text emptyField;
	public Text shortPassword;
	public Text passwordMatch;
	public Text zipCodeNumber;
	public Text zipCodeDigits;
	public ChoiceBox<String> newState;
	
	public Pane logInFields;
	
	
	private void updateLoginDate(String username, String password) throws SQLException {
		
		//Creates a string based on the current date
		String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY/MM/dd"));
		
		st.executeUpdate(
				"UPDATE USER_INFORMATION "
				+ ""
				+ "SET LAST_LOGIN = '" + formatDateTime
				+ ""
				+ "' WHERE USERNAME = '" + username
				+ "' AND PASSWORD = '" + password + "'"
		);
		
	}
	
	//alert box for successful database insertion
	public Stage insertSuccessful() {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText("User successfully created.");
		
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		
		return window;
		
	}
	
	
	public void createNewAccount(ActionEvent e) throws SQLException {
		
		//clear shown texts
		duplicateUsername.setVisible(false);
		emptyField.setVisible(false);
		shortPassword.setVisible(false);
		passwordMatch.setVisible(false);
		zipCodeNumber.setVisible(false);
		zipCodeDigits.setVisible(false);
		
		//setup SQL query to test duplicate username
		String userExists = "SELECT 1 "
				+ "FROM USER_INFORMATION "
				+ "WHERE USERNAME = '" + newUsername.getText() + "'";
		
		rs = st.executeQuery(userExists);
		
		//assign values from the text boxes
		String username = newUsername.getText().toLowerCase();
		String password = hash.hashString(newPassword.getText());
		String firstName = newFirstName.getText();
		String lastName = newLastName.getText();
		String address = newAddress.getText();
		String phoneNumber = newPhoneNumber.getText().replaceAll("[^0-9]+", "");
		String city = newCity.getText();
		String state = newState.getValue();
		String zipCodeString = newZipCode.getText();
		int zipCode;
		
		final String defaultArraySerial = "rO0ABXVyADhbTGlvLmdpdGh1Yi5OYXRlYWNvZmZleS5BY2NvdW50cy5Vc2VyQWNjb3VudEluZm9ybWF0aW9uO++U7eBH5PqdAgAAeHAAAAAFcHBwcHA=";
		
		String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY/MM/dd"));
		
		//checks if zip code is a number
		try {
			zipCode = Integer.parseInt(zipCodeString);
			
			if(zipCode < 1 || zipCode > 99999) {
				zipCodeDigits.setVisible(true);
				return;
			}
		}catch(NumberFormatException nfe) {
			zipCodeNumber.setVisible(true);
			return;
		}
		
		
		//check if any text box is empty
		if(username.isEmpty() || password.isEmpty() ||
				firstName.isEmpty() || lastName.isEmpty() ||
				address.isEmpty() || phoneNumber.isEmpty() ||
				city.isEmpty() ||state.isEmpty() ) {
				//end if conditional
			
			emptyField.setVisible(true);
			
		}
		//checks if username already exists
		else if(rs.isBeforeFirst()) {
			duplicateUsername.setVisible(true);
		}
		//check password length
		else if(newPassword.getText().length() < 8) {
			shortPassword.setVisible(true);
		}
		//verify passwords are the same
		else if(!newPassword.getText().equals(newPasswordConfirm.getText())) {
			passwordMatch.setVisible(true);
		}
		//create new user and insert into database
		else {
			
			String userDBInfo = "INSERT INTO USER_INFORMATION "
					+ "(USERNAME, PASSWORD, "
					+ "FIRST_NAME, LAST_NAME, "
					+ "ADDRESS, CITY, STATE, ZIP_CODE, "
					+ "PHONE_NUMBER, "
					+ "ACCOUNT_SERIAL, AMOUNT_OF_ACCOUNTS, "
					+ "LAST_LOGIN) "
					+ "VALUES "
					+ ""
					+ "('" + username + "', '" + password
					+ "', '" + firstName + "', '" + lastName
					+ "', '" + address + "', '" + city + "', '" + state + "', '" + zipCode
					+ "', '" + phoneNumber
					+ "', '" + defaultArraySerial + "', 0, "
					+ "'" + formatDateTime + "')"
					+ "";
			
			st.executeUpdate(userDBInfo);
			
			//create forced alert box
			Stage window = insertSuccessful();
			window.showAndWait();
			
			//show main log in screen and clear textfields
			newAccount.setVisible(false);
			logInFields.setVisible(true);
			
			newUsername.setText("");
			newPassword.setText("");
			newPasswordConfirm.setText("");
			newFirstName.setText("");
			newLastName.setText("");
			newAddress.setText("");
			newCity.setText("");
			newState.setValue(null);
			newZipCode.setText("");
			newPhoneNumber.setText("");
			
		}//end if - else if - else condition
		
		
	}
	
	
	
	public void closeNewAccount(ActionEvent e) {
		newAccount.setVisible(false);
		logInFields.setVisible(true);
	}
	
	public void showNewAccount(ActionEvent e) {
		
		logInFields.setVisible(false);
		newAccount.setVisible(true);
	}
	
	
	//pressing <enter> on password field
	public void userSearch(ActionEvent e) throws SQLException, IOException {
		userNotFound.setVisible(false);
		
		
		VerifyUser verify = new VerifyUser();
		
		usernameLocked = username.getText().toLowerCase();
		
		if(!usernameLocked.isEmpty())
			rs = verify.TestUsernamePassword( st, usernameLocked, hash.hashString(password.getText()) );
		else
			rs = null;
		
		if(rs.isBeforeFirst() && rs != null) {
			
			updateLoginDate(usernameLocked, hash.hashString(password.getText()));
			
			//switches scenes depending on if admin is logging in
			if(!usernameLocked.equals("adminadmin")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/io/github/Nateacoffey/Accounts/AccountPage.fxml"));
				Parent root = loader.load();
				
				Scene accountPageScene = new Scene(root);
				
				//Sends data to the controller before showing
				AccountPageController accountController = loader.getController();
				accountController.setUserInfo(rs, st);//error TODO
				
				Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
				
				window.setScene(accountPageScene);
				window.show();
			}else {
				
				String adminDBQuery = "SELECT AMOUNT_OF_ACCOUNTS, "
										+ "FIRST_NAME, "
										+ "LAST_LOGIN, "
										+ "STATE, "
										+ "ZIP_CODE "
										+ ""
										+ "FROM USER_INFORMATION";
				
				ResultSet dBQuery = st.executeQuery(adminDBQuery);
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/io/github/Nateacoffey/Admin/AdminPage.fxml"));
				Parent root = loader.load();
				
				Scene accountPageScene = new Scene(root);
				
				//Sends data to the controller before showing
				AdminPageController accountController = loader.getController();
				accountController.fillDatabaseArray(dBQuery);
				
				Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
				
				window.setScene(accountPageScene);
				window.show();
			}//end nest if-else
			
		} else {
			userNotFound.setVisible(true);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		try {
			/* 
			 * https://stackoverflow.com/questions/21955256/manipulating-an-access-database-from-java-without-odbc
			 * 
			 * http://ucanaccess.sourceforge.net/site.html
			 * 
			 * 
			 * add  5 jars to library (all inside UCanAccess-5.0.0-bin.zip)
			 * 
			 * ucanaccess-5.0.0.jar
			 * commons-langs3-3.8.1.jar
			 * commons.logging-1.2.jar
			 * hsqldb.jar
			 * jackcess-3.0.1.jar
			 * 
			 * 
			 */
			
			
		//connect to path of database
		dbConnection = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/Nathan/git/BankOfCoffey/Bank of Coffey/BankOfCoffeyDatabase.accdb");
		st = dbConnection.createStatement();
		
		
		//load newState choice box list
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.removeAll(list);
		String[] states = {	"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
							"HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
							"MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
							"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
							"SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
		
		list.addAll(states);
		
		newState.getItems().addAll(list);
		
		
		}
		catch(SQLException e) {
			System.out.println("ERROR:\n-" + e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("ERROR:\n-" + e.getMessage());
			e.printStackTrace();
		}
	
	}
		
	
	
}
