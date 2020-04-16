package io.github.Nateacoffey.Accounts;

public class Transfer {
	boolean MoneyTransfer(int accountOut, double amount, int accountIn) {
		
		Withdrawal withdraw = new Withdrawal();
		Deposit deposit = new Deposit();
		
		
		//sends the transfer amount to withdraw and then deposit from selected account
		if( amount > 0 && withdraw.Withdraw(accountOut, amount)) {
			
			if(deposit.Insert(accountIn, amount)) {
				
				return true;
				
			}else {
				
				return false;
				
			}
			
		}
		
		
		return false;
		
	}
}
