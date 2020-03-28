package io.github.Nateacoffey.Sorting;

import io.github.Nateacoffey.Application.DatabaseUsersInformation;

public class BubbleSort {
	
	public DatabaseUsersInformation[] sort(DatabaseUsersInformation[] array, String column) {
		
		int arrLength = array.length;
		Integer intWrapI;
		Integer intWrapJ;
		
		//length - 1: last element cant get compared
		for(int i = 0; i < arrLength - 1; i++) {
			//decrease the max value: the last element is always sorted
			for(int j = 0; j < arrLength - i - 1; j++) {
				
				int compare = 0;
				
				//compare depending on the column sort
				switch(column) {
					case "Amount of Accounts":
						intWrapI = new Integer(array[j].getAmountOfAccounts());
						intWrapJ = new Integer(array[j + 1].getAmountOfAccounts());
						
						compare = intWrapI.compareTo(intWrapJ);
						break;
					case "First Name":
						compare = array[j].getFirstName().compareTo(array[j + 1].getFirstName());
						break;
					case "Latest Login":
						compare = array[j].getLatestLogin().compareTo(array[j + 1].getLatestLogin()) * -1;//reverse order for date
						break;
					case "State":
						compare = array[j].getState().compareTo(array[j + 1].getState());
						break;
					case "Zip Code":
						intWrapI = new Integer(array[j].getZipCode());
						intWrapJ = new Integer(array[j + 1].getZipCode());
						
						compare = intWrapI.compareTo(intWrapJ);
						break;
				};
				
				//swapping values
				if(compare > 0) {
					
					DatabaseUsersInformation temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					
				}
				
			}//end nest for
		}//end for
		
		return array;
	}
}
