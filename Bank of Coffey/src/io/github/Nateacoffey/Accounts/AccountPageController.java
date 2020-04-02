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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AccountPageController implements Initializable {
	
	Statement st;
	
	UserInformation user;
	UserAccountInformation userObject;
	
	public TextField test;
	public Text greeting;
	
	public TableView<UserAccountInformation> table;
	
	public Text emptyChoice;
	public Text notANumber;
	public Text accountNotFound;
	
	public DialogPane profile;
	public Text userName;
	public Text userAddress;
	public Text userCity;
	public Text userState;
	public Text userZipCode;
	public Text userPhoneNumber;
	
	public ChoiceBox<Integer> withdrawAccountList;
	public TextField withdrawAccountText;
	
	public ChoiceBox<Integer> depositAccountList;
	public TextField depositAccountText;
	
	public ChoiceBox<Integer> transferFromAccountList;
	public TextField transferAccountText;
	public ChoiceBox<Integer> transferToAccountList;
	
	public ChoiceBox<String> addAccountList;
	
	public ChoiceBox<Integer> deleteAccountList;
	
	
	//sets and displays the proper information
	public void setUserInfo(ResultSet rs, Statement st) throws SQLException {
		
		this.st = st;
		
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
		
		
		fillTable();
	}
	
	public void serialAccount() {
		
		ArraySerial serialize = new ArraySerial();
		
		serialize.serial(user.getUsername(), st);
		
	}
	
	
	public void fillTable() {
		
		table.getItems().clear();
		
		ObservableList<UserAccountInformation> userAccountList = FXCollections.observableArrayList();
		
		
		for(UserAccountInformation uai : UserInformation.ArrayOfAccounts) {
			
			if(uai != null) {
				
				userAccountList.add(uai);
				
			}
		}
		
		table.getItems().addAll(userAccountList);
		
	}
	
	
	public void withdrawFunds(ActionEvent e) {
		emptyChoice.setVisible(false);
		notANumber.setVisible(false);
		accountNotFound.setVisible(false);
		accountNotFound.setText("");
		
		if(withdrawAccountList.getValue() == null || withdrawAccountText.getText() == null) {
			emptyChoice.setVisible(true);
			return;
		}
		
		Withdrawal withdraw = new Withdrawal();
		
		try {
			int accountId = withdrawAccountList.getValue().intValue();
			double money = Double.valueOf(withdrawAccountText.getText());
			
			if(UserInformation.ArrayOfAccounts[accountId] != null) {
				if(withdraw.Withdraw(accountId, money)) {
					
					withdrawAccountText.setText("");
					withdrawAccountList.setValue(null);
					
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
		
		if(depositAccountList.getValue() == null || depositAccountText.getText() == null) {
			emptyChoice.setVisible(true);
			return;
		}
		
		Deposit deposit = new Deposit();
		
		try {
			int accountId = depositAccountList.getValue().intValue();
			double money = Double.valueOf(depositAccountText.getText());
			
			if(UserInformation.ArrayOfAccounts[accountId] != null) {
				if(deposit.Insert(accountId, money)) {
					
					depositAccountText.setText("");
					depositAccountList.setValue(null);
					
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
			
			if(UserInformation.ArrayOfAccounts[accountIdFrom] != null && UserInformation.ArrayOfAccounts[accountIdTo] != null) {
				if(money > 0) {
					if(transfer.MoneyTransfer(accountIdFrom, money, accountIdTo)){
						
						transferFromAccountList.setValue(null);
						transferAccountText.setText("");
						transferToAccountList.setValue(null);
						
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
		
		if(addAccount.AddAccountToArray(addAccountList.getValue())) {
			
			addAccountList.setValue(null);
			
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
		
		if(deleteAccount.DeleteFromArray(deleteAccountList.getValue())) {
			
			deleteAccountList.setValue(null);
			
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
		
		user = null;//empties user data before switching scenes
		
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
		idColumn.setMinWidth(table.getPrefWidth() * 0.1);
		idColumn.setStyle("-fx-alignment: CENTER");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<UserAccountInformation, String> accountColumn = new TableColumn<>("Account");
		accountColumn.setMinWidth(table.getPrefWidth() * 0.6);
		accountColumn.setStyle("-fx-alignment: CENTER");
		accountColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
		
		
		TableColumn<UserAccountInformation, Double> balanceColumn = new TableColumn<>("Balance");
		balanceColumn.setMinWidth(table.getPrefWidth() * 0.3);
		balanceColumn.setStyle("-fx-alignment: CENTER");
		balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
		
		table.getColumns().add(idColumn);
		table.getColumns().add(accountColumn);
		table.getColumns().add(balanceColumn);
		
	}
}
