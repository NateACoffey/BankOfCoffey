package io.github.Nateacoffey.Admin;

public class DatabaseUsersInformation {
	
	
	private int amountOfAccounts = 0;
	private String firstName = "";
	private String latestLogin = "";
	private String state = "";
	private int zipCode = 0;
	
	public DatabaseUsersInformation(int amountOfAccounts, String firstName, String latestLogin, String state, int zipCode) {
		
		this.amountOfAccounts = amountOfAccounts;
		this.firstName = firstName;
		this.latestLogin = latestLogin;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	
	public int getAmountOfAccounts() {
		return amountOfAccounts;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLatestLogin() {
		return latestLogin;
	}
	
	public String getState() {
		return state;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	
	public String toString() {
		return firstName + "\t\t" + state + "\t\t" + zipCode + "\t\t" + amountOfAccounts + "\t\t" + latestLogin + "\n";
	}
}
