package io.github.Nateacoffey.Sorting;

import io.github.Nateacoffey.Admin.DatabaseUsersInformation;

public class InsertionSort {
	
	public int switchComparison(String column, DatabaseUsersInformation[] array, int j, DatabaseUsersInformation key) {
		
		Integer intWrapI;
		Integer intWrapJ;
		
		int compare = 0;
		
		//compare depending on the column sort
		switch(column) {
			case "Amount of Accounts":
				intWrapI = new Integer(array[j].getAmountOfAccounts());
				intWrapJ = new Integer(key.getAmountOfAccounts());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
			case "First Name":
				compare = array[j].getFirstName().compareTo(key.getFirstName());
				break;
			case "Latest Login":
				compare = array[j].getLatestLogin().compareTo(key.getLatestLogin()) * -1;//reverse order for date
				break;
			case "State":
				compare = array[j].getState().compareTo(key.getState());
				break;
			default://case Zip Code
				intWrapI = new Integer(array[j].getZipCode());
				intWrapJ = new Integer(key.getZipCode());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
		};
		
		return compare;
	}
	
	
	public DatabaseUsersInformation[] sort(DatabaseUsersInformation[] array, String column) {
		
		int arrLength = array.length;
		
		
		
		for(int i = 1; i < arrLength; ++i) {
			DatabaseUsersInformation key = array[i];
			int j = i - 1;
			
			int compare;
			
			//compares based on sorted field
			compare = switchComparison(column, array, j, key);
			
			
			//move all elements greater than the Object array value to the right one
			while(j >= 0 && compare > 0) {
				array[j + 1] = array[j];
				j -= 1;
				
				//re-compares the Object array values
				if(j >= 0)
					compare = switchComparison(column, array, j, key);
			}
			
			array[j + 1] = key;
			
		}
		
		
		return array;
	}
}
