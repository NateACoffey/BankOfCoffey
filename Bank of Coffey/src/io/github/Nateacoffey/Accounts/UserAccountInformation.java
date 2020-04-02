package io.github.Nateacoffey.Accounts;

public class UserAccountInformation implements java.io.Serializable {
	
	private static final long serialVersionUID = 4L;
	

	private enum AccountType{
		CHECKING, SAVINGS, INTEREST_BEARING, MONEY_MARKET
	}
	
	private AccountType accountType;
	private double balance;
	private int id;
	
	
	UserAccountInformation(String accountType, int id) {
		
		AccountType typeValue = AccountType.valueOf(accountType.toUpperCase());
		
		this.accountType = typeValue;
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
