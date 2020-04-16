package io.github.Nateacoffey.Accounts;

public class DeleteUserAccount {
	boolean DeleteFromArray(int arrayElement){
		
		UserAccountInformation[] accounts = UserInformation.arrayOfAccounts;
		
		int length = accounts.length;
		
		//verifies the account exists and is within the User static array length
		if(arrayElement < length && arrayElement >= 0 && accounts[arrayElement] != null) {
			accounts[arrayElement] = null;
			
			return true;
		}
		
		return false;
	}
}
