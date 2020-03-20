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

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;

public class LogInController implements Initializable {
	
	Statement st;
	ResultSet rs;
	Connection dbConnection;
	
	private String usernameLocked;	//locks the username to prevent alteration
	
	public TextField username;
	public PasswordField password;
	public Text userNotFound;
	
	
	private void updateLogin(String username, String password) throws SQLException {
		
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
	
	
	//pressing <enter> on password field
	public void userSearch(ActionEvent e) throws SQLException, IOException {
		
		Hash hash = new Hash();
		VerifyUser verify = new VerifyUser();
		
		usernameLocked = username.getText().toLowerCase();
		
		if(!usernameLocked.isEmpty())
			rs = verify.TestUsernamePassword( st, usernameLocked, hash.hashString(password.getText()) );
		else
			rs = null;
		
		if(rs.isBeforeFirst() && rs != null) {
            
			updateLogin(usernameLocked, hash.hashString(password.getText()));
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("AccountPage.fxml"));
			Parent root = loader.load();
			
			Scene accountPageScene = new Scene(root);
			
			AccountPageController accountController = loader.getController();
			accountController.getResultSet(rs);
			
			
			Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			
			window.setScene(accountPageScene);
			window.show();
			
			
		} else {
			userNotFound.setVisible(true);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(dbConnection.equals(null)) {
		
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
	
}
