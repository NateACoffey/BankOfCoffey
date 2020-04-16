package io.github.Nateacoffey.Accounts;

public class Deposit {
	boolean Insert(int accountId, double amount) {
		
		
		double originalBalance = UserInformation.arrayOfAccounts[accountId].getBalance();
		double endBalance = originalBalance + amount;
		
		
		//check for overflow 
		if(endBalance > 0 && amount > 0) {
			UserInformation.arrayOfAccounts[accountId].setBalance(endBalance);
			
			return true;
			
		}else {
		
			return false;
			
		}
	}
}
