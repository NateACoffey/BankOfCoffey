package io.github.Nateacoffey.Application;

public class DeleteUserAccount {
	boolean DeleteFromArray(int arrayElement){
		
		UserAccountInformation[] accounts = UserInformation.ArrayOfAccounts;
		
		if(arrayElement < accounts.length && arrayElement >= 0 && accounts[arrayElement] != null) {
			accounts[arrayElement] = null;
			
			return true;
		}
		
		return false;
	}
}
