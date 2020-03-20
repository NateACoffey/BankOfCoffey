package io.github.Nateacoffey.Application;

public class Deposit {
	public double Insert(double balance, double amount) {
		
		double endBalance = balance + amount;
		
		
		//check for overflow
		if(endBalance < 0)	
			return balance;
		
		
		return endBalance;
		
	}
}
