package io.github.Nateacoffey.Accounts;

public class Withdrawal {
	boolean Withdraw(int accountId, double amount) {
		
		
		double originalBalance = UserInformation.ArrayOfAccounts[accountId].getBalance();
		double endBalance = originalBalance - amount;
		
		//check if requested amount or result is negative
		if(amount < 0 || endBalance < 0) {
			return false;
		}
		
		//check for underflow
		if(endBalance >= originalBalance) {
			return false;
		}
		
		
		UserInformation.ArrayOfAccounts[accountId].setBalance(endBalance);
		
		
		return true;
		
	}
}
