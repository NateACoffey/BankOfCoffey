package io.github.Nateacoffey.Accounts;

public class Deposit {
	double Insert(double balance, double amount) {
		
		double endBalance = balance + amount;
		
		
		//check for overflow
		if(endBalance < 0)	
			return balance;
		
		
		return endBalance;
		
	}
}
