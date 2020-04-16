package io.github.Nateacoffey.Accounts;

public class Withdrawal {
	boolean Withdraw(int accountId, double amount) {
		
		
		double originalBalance = UserInformation.arrayOfAccounts[accountId].getBalance();
		double endBalance = originalBalance - amount;
		
		
		//check if requested amount or result is negative and checking for underflow
		if(amount > 0 && endBalance > 0 && endBalance < originalBalance) {
			
			UserInformation.arrayOfAccounts[accountId].setBalance(endBalance);
			
			
			return true;
			
		}else {
			
			return false;
			
		}
		
		
	}
}
