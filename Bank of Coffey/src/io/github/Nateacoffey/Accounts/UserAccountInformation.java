package io.github.Nateacoffey.Accounts;

public class UserAccountInformation implements java.io.Serializable {
	
	private static final long serialVersionUID = 4L;
	
	
	//created enum type for the types of accounts that can be created
	private enum AccountType{
		CHECKING, SAVINGS, INTEREST_BEARING, MONEY_MARKET
	}
	
	private AccountType accountType;
	private double balance;
	private int id;
	
	
	UserAccountInformation(String typeValue, int id) {
		//converts string into enumeration
		AccountType accountType = AccountType.valueOf(typeValue.toUpperCase());
		
		this.accountType = accountType;
		this.id = id;
		this.balance = 0.0;
	}
	
	
	void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getAccountType(){
		return accountType.toString().replace("_", " ");
	}
	
	public double getBalance() {
		return balance;
	}
	
	public int getId() {
		return id;
	}
}
