package io.github.Nateacoffey.Accounts;

import java.time.LocalDate;

public class UserInformation {
	
	
	public static UserAccountInformation[] arrayOfAccounts = new UserAccountInformation[5]; 
	
	
	private String username = "";
	
	private String firstName = "";
	private String lastName = "";
	
	private String address = "";
	private String city = "";
	private String state = "";
	private int zipCode = 0;
	
	private String phoneNumber = "";
	
	private int amountOfAccounts = 0;
	
	private LocalDate lastLogin;//admin purposes only
	
	public UserInformation() {}
	
	UserInformation(String username,
							String firstName,
							 String lastName,
							  String address,
							   String city,
								String state,
								 int zipCode,
								  String phoneNumber,
								   String accountSerial,
								    int amountOfAccounts
							) {
		
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
		this.amountOfAccounts = amountOfAccounts;
		
		ArraySerial serial = new ArraySerial();
		UserInformation.arrayOfAccounts = serial.deserial(accountSerial);
	}
	
	
	public void setAmountOfAccounts(int amountOfAccounts) {
		this.amountOfAccounts = amountOfAccounts;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public int getAmountOfAccounts() {
		return amountOfAccounts;
	}
	
	public LocalDate getLastLogin() {
		return lastLogin;
	}
	
}




























