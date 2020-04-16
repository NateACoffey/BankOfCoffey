package io.github.Nateacoffey.Accounts;

public class AddUserAccount {
	boolean AddAccountToArray(String accountType) {
		
		UserAccountInformation[] accounts = UserInformation.arrayOfAccounts;
		
		int length = accounts.length;
		
		for(int i = 0; i < length; i++) {
			
			if(accounts[i] == null ) {
				//add the account to the first array then immediately return true
				UserAccountInformation accountInfo = new UserAccountInformation(accountType, i);
				
				accounts[i] = accountInfo;
				
				return true;
			}
			
			
			//if loop does not find an empty element, return false
			if(i == length - 1) {
				return false;
			}
			
		}
		
		return false;
		
		
	}
}
