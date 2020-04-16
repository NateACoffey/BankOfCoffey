package io.github.Nateacoffey.Accounts;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AccountPageController implements Initializable {
	
	final int userAccountArrayLength = UserInformation.arrayOfAccounts.length;
	
	Statement databaseSQLStatement;
	
	UserInformation userProfileInformation;
	
	@FXML private Text greeting;
	
	@FXML private TableView<UserAccountInformation> userAccountTable;
	
	@FXML private Text emptyChoice;
	@FXML private Text notANumber;
	@FXML private Text accountNotFound;
	
	@FXML private DialogPane profile;
	@FXML private Text userName;
	@FXML private Text userAddress;
	@FXML private Text userCity;
	@FXML private Text userState;
	@FXML private Text userZipCode;
	@FXML private Text userPhoneNumber;
	
	@FXML private ChoiceBox<Integer> withdrawAccountList;
	@FXML private TextField withdrawAccountText;
	
	@FXML private ChoiceBox<Integer> depositAccountList;
	@FXML private TextField depositAccountText;
	
	@FXML private ChoiceBox<Integer> transferFromAccountList;
	@FXML private TextField transferAccountText;
	@FXML private ChoiceBox<Integer> transferToAccountList;
	
	@FXML private ChoiceBox<String> addAccountList;
	
	@FXML private ChoiceBox<Integer> deleteAccountList;
	
	
	//sets and displays the proper information
	public void setUserInfo(ResultSet rs, Statement databaseSQLStatement) throws SQLException {
		
		this.databaseSQLStatement = databaseSQLStatement;
		
		rs.next();
		
		userProfileInformation = new UserInformation(rs.getString("USERNAME"),
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
		
		greeting.setText("Hello, " + userProfileInformation.getFirstName());
		
		userName.setText(userProfileInformation.getFirstName() + " " + userProfileInformation.getLastName());
		userAddress.setText(userProfileInformation.getAddress());
		userCity.setText(userProfileInformation.getCity());
		userState.setText(userProfileInformation.getState());
		userZipCode.setText(Integer.toString(userProfileInformation.getZipCode()));
		userPhoneNumber.setText(userProfileInformation.getPhoneNumber());
		
		
		fillTable();
	}
	
	public void serialAccount() {
		
		ArraySerial serialize = new ArraySerial();
		
		serialize.serial(userProfileInformation.getUsername(), databaseSQLStatement);
		
	}
	
	
	//Fills the table with the User's accounts
	public void fillTable() {
		
		userAccountTable.getItems().clear();
		
		ObservableList<UserAccountInformation> userAccountList = FXCollections.observableArrayList();
		
		//iterates through the User static array and adds all non-null element to the list
		for(UserAccountInformation userObject : UserInformation.arrayOfAccounts) {
			
			if(userObject != null) {
				
				userAccountList.add(userObject);
				
			}
		}
		
		userAccountTable.getItems().addAll(userAccountList);
		
	}
	
	
	public void withdrawFunds(ActionEvent e) {
		emptyChoice.setVisible(false);
		notANumber.setVisible(false);
		accountNotFound.setVisible(false);
		accountNotFound.setText("");
		
		//if withdraw fields are empty
		if(withdrawAccountList.getValue() == null || withdrawAccountText.getText() == null) {
			emptyChoice.setVisible(true);
			return;
		}
		
		Withdrawal withdraw = new Withdrawal();
		
		try {
			int accountId = withdrawAccountList.getValue().intValue();
			double money = Double.valueOf(withdrawAccountText.getText());
			
			//verifies the account ID  exists
			if(UserInformation.arrayOfAccounts[accountId] != null) {
				if(withdraw.Withdraw(accountId, money)) {
					
					//empty withdraw fields
					withdrawAccountText.setText("");
					withdrawAccountList.setValue(null);
					
					//refreshes the table with new values and serializes the array to the database
					fillTable();
					serialAccount();
					
				}else {
					accountNotFound.setVisible(true);
					accountNotFound.setText("Error withdrawing money.");
				}
			}else {
				accountNotFound.setVisible(true);
				accountNotFound.setText("Account does not exist.");
			}
		
		}catch(NumberFormatException nfe) {
			notANumber.setVisible(true);
		}
	}
	
	public void depositFunds(ActionEvent e) {
		emptyChoice.setVisible(false);
		notANumber.setVisible(false);
		accountNotFound.setVisible(false);
		accountNotFound.setText("");
		
		//verifies deposit fields are not empty
		if(depositAccountList.getValue() == null || depositAccountText.getText() == null) {
			emptyChoice.setVisible(true);
			return;
		}
		
		Deposit deposit = new Deposit();
		
		try {
			int accountId = depositAccountList.getValue().intValue();
			double money = Double.valueOf(depositAccountText.getText());
			
			if(UserInformation.arrayOfAccounts[accountId] != null) {
				if(deposit.Insert(accountId, money)) {
					
					//empties deposit fields
					depositAccountText.setText("");
					depositAccountList.setValue(null);
					
					//refreshes the table with new values and serializes the array to the database
					fillTable();
					serialAccount();
					
				}else {
					accountNotFound.setVisible(true);
					accountNotFound.setText("Error depositing money");
				}
			}else {
				accountNotFound.setVisible(true);
				accountNotFound.setText("Account does not exist.");
			}
			
		}catch(NumberFormatException nfe) {
			notANumber.setVisible(true);
		}
	}
	
	public void transferFunds(ActionEvent e) {
		emptyChoice.setVisible(false);
		notANumber.setVisible(false);
		accountNotFound.setVisible(false);
		accountNotFound.setText("");
		
		//verifies transfer fields are not empty
		if(transferFromAccountList.getValue() == null 
				|| transferAccountText.getText() == null 
				|| transferToAccountList.getValue() == null) {
			
			emptyChoice.setVisible(true);
			return;
		}
		
		Transfer transfer = new Transfer();
		
		try {
			int accountIdFrom = transferFromAccountList.getValue().intValue();
			double money = Double.valueOf(transferAccountText.getText());
			int accountIdTo = transferToAccountList.getValue().intValue();
			
			if(UserInformation.arrayOfAccounts[accountIdFrom] != null && UserInformation.arrayOfAccounts[accountIdTo] != null) {
				if(money > 0) {
					if(transfer.MoneyTransfer(accountIdFrom, money, accountIdTo)){
						
						//empties transfer fields
						transferFromAccountList.setValue(null);
						transferAccountText.setText("");
						transferToAccountList.setValue(null);
						
						//refreshes the table with new values and serializes the array to the database
						fillTable();
						serialAccount();
						
					}else {
						accountNotFound.setVisible(true);
						accountNotFound.setText("Error transferring funds");
					}
					
				}else {
					accountNotFound.setVisible(true);
					accountNotFound.setText("You cannot transfer negative funds");
				}
				
			}else {
				accountNotFound.setVisible(true);
				accountNotFound.setText("Error transferring money");
			}
			
		}catch(NumberFormatException nfe) {
			notANumber.setVisible(true);
		}
		
	}
	
	public void addAccount(ActionEvent e) {
		emptyChoice.setVisible(false);
		notANumber.setVisible(false);
		accountNotFound.setVisible(false);
		accountNotFound.setText("");
		
		if(addAccountList.getValue() == null) {
			emptyChoice.setVisible(true);
			return;
		}
		
		AddUserAccount addAccount = new AddUserAccount();
		
		//returns false if the User static array is already full
		if(addAccount.AddAccountToArray(addAccountList.getValue())) {
			
			addAccountList.setValue(null);
			
			//refreshes the table with new values and serializes the array to the database
			fillTable();
			serialAccount();
			
		}else {
			accountNotFound.setVisible(true);
			accountNotFound.setText("You cannot have more than 5 accounts.");
			
		}
		
	}
	
	public void deleteAccount(ActionEvent e) {
		emptyChoice.setVisible(false);
		notANumber.setVisible(false);
		accountNotFound.setVisible(false);
		accountNotFound.setText("");
		
		if(deleteAccountList.getValue() == null) {
			emptyChoice.setVisible(true);
			return;
		}
		
		DeleteUserAccount deleteAccount = new DeleteUserAccount();
		
		//returns false if the account ID does not exist
		if(deleteAccount.DeleteFromArray(deleteAccountList.getValue())) {
			
			deleteAccountList.setValue(null);
			
			//refreshes the table with new values and serializes the array to the database
			fillTable();
			serialAccount();
			
		}else {
			accountNotFound.setVisible(true);
			accountNotFound.setText("Error deleting account");
		}
		
	}
	
	
	
	public void toLogInScreen(ActionEvent e) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/io/github/Nateacoffey/Application/LogInScreen.fxml"));
		Parent root = loader.load();
		
		Scene accountPageScene = new Scene(root);
		
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		userProfileInformation = null;//empties user data before switching scenes
		
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
		//adding account types
		ObservableList<String> accountList = FXCollections.observableArrayList();
		
		accountList.removeAll(accountList);
		String[] accountTypes = {	"CHECKING", "SAVINGS", "INTEREST_BEARING", "MONEY_MARKET"};
		
		accountList.addAll(accountTypes);
		
		addAccountList.getItems().addAll(accountList);
		
		//number list for account manipulating
		ObservableList<Integer> numberList = FXCollections.observableArrayList();
		
		numberList.removeAll(numberList);
		Integer[] numbers = {0, 1, 2, 3, 4};
		
		numberList.addAll(numbers);
		
		//adding them to choiceboxes
		withdrawAccountList.getItems().addAll(numberList);
		depositAccountList.getItems().addAll(numberList);
		transferFromAccountList.getItems().addAll(numberList);
		transferToAccountList.getItems().addAll(numberList);
		deleteAccountList.getItems().addAll(numberList);
		
		
		
		
		//set tableview columns
		TableColumn<UserAccountInformation, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(userAccountTable.getPrefWidth() * 0.1);
		idColumn.setStyle("-fx-alignment: CENTER");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<UserAccountInformation, String> accountColumn = new TableColumn<>("Account");
		accountColumn.setMinWidth(userAccountTable.getPrefWidth() * 0.6);
		accountColumn.setStyle("-fx-alignment: CENTER");
		accountColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
		
		
		TableColumn<UserAccountInformation, Double> balanceColumn = new TableColumn<>("Balance");
		balanceColumn.setMinWidth(userAccountTable.getPrefWidth() * 0.3);
		balanceColumn.setStyle("-fx-alignment: CENTER");
		balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
		
		userAccountTable.getColumns().add(idColumn);
		userAccountTable.getColumns().add(accountColumn);
		userAccountTable.getColumns().add(balanceColumn);
		
	}
}
