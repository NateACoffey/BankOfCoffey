package io.github.Nateacoffey.Application;

public class Transfer {
	void MoneyTransfer(int accountOut, int accountIn, double amount) {
		Withdrawal withdraw = new Withdrawal();
		Deposit deposit = new Deposit();
		
		UserAccountInformation[] accounts = UserInformation.ArrayOfAccounts;
		
		double balance;
		
		//Take the money out of the account we are transferring from
		balance = accounts[accountOut].getBalance(); 
		
		accounts[accountOut].setBalance(withdraw.Withdraw(balance, amount));
		
		//Put the money into the account we transferring to
		balance = accounts[accountIn].getBalance();
		
		accounts[accountIn].setBalance(deposit.Insert(balance, amount));
		
		
	}
}
