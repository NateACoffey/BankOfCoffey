package io.github.Nateacoffey.Accounts;

public class AddUserAccount {
	boolean AddAccountToArray(String accountType) {
		
		UserAccountInformation[] accounts = UserInformation.ArrayOfAccounts;
		
		for(int i = 0; i < accounts.length; i++) {
			
			if(accounts[i] == null ) {
				//add the account to the first array then immediately return true
				UserAccountInformation accountInit = new UserAccountInformation(accountType, i);
				
				accounts[i] = accountInit;
				
				return true;
			}
			
			
			//if loop does not find an empty element, return false
			if(i == accounts.length - 1) {
				return false;
			}
			
		}
		
		return false;
		
		
	}
}
