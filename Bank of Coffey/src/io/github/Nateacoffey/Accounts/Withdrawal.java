package io.github.Nateacoffey.Accounts;

public class Withdrawal {
	public double Withdraw(double balance, double amount) {
		
		double endBalance = balance - amount;
		
		
		//check if requested amount or result is negative
		if (amount < 0 || endBalance < 0) 
			return balance;
		
		//check for underflow
		if(endBalance >= balance)	
			return balance;
		
		
		return endBalance;
		
	}
}
