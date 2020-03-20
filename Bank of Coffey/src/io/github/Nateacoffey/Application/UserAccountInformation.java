package io.github.Nateacoffey.Application;

public class UserAccountInformation implements java.io.Serializable {
	
	private static final long serialVersionUID = 4L;
	

	private enum AccountType{
		CHECKING, SAVINGS, INTEREST_BEARING, MONEY_MARKET
	}
	
	private AccountType accountType;
	private double balance;
	
	
	UserAccountInformation(String accountType) {
		
		AccountType typeValue = AccountType.valueOf(accountType.toUpperCase());
		
		this.accountType = typeValue;
		this.balance = 0;
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
	
}
