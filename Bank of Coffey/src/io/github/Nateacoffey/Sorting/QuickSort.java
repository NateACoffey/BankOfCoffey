package io.github.Nateacoffey.Sorting;

import io.github.Nateacoffey.Admin.DatabaseUsersInformation;

public class QuickSort {
	
	private String column;
	
	public int switchComparison(DatabaseUsersInformation[] array, int i, DatabaseUsersInformation right) {
		
		Integer intWrapI;
		Integer intWrapJ;
		
		int compare = 0;
		
		//compare depending on the column sort
		switch(column) {
			case "Amount of Accounts":
				intWrapI = new Integer(array[i].getAmountOfAccounts());
				intWrapJ = new Integer(right.getAmountOfAccounts());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
			case "First Name":
				compare = array[i].getFirstName().compareTo(right.getFirstName());
				break;
			case "Latest Login":
				compare = array[i].getLatestLogin().compareTo(right.getLatestLogin()) * -1;//reverse order for date
				break;
			case "State":
				compare = array[i].getState().compareTo(right.getState());
				break;
			default://case Zip Code
				intWrapI = new Integer(array[i].getZipCode());
				intWrapJ = new Integer(right.getZipCode());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
		};
		
		return compare;
	}
	
	private int partition(DatabaseUsersInformation[] array, int low, int high) {
		
		DatabaseUsersInformation pivot = array[high];
		int i = low - 1;
		
		for(int j = low; j < high; j++) {
			//trades the current element (j) with the element to the right of the higher value element less than the pivot (i) in the array
			if(switchComparison(array, j, pivot) < 0 ) {
				i++;
				
				DatabaseUsersInformation temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		
		//final swap of the pivot with the highest value element that is smaller than the pivot
		DatabaseUsersInformation temp = array[i + 1];
		array[i + 1] = array[high];
		array[high] = temp;
		
		
		return i + 1;
	}
	
	private DatabaseUsersInformation[] quickSort(DatabaseUsersInformation[] array, int low, int high) {
		
		if(low < high) {
			int part = partition(array, low, high);
			
			array = quickSort(array, low, part - 1);
			array = quickSort(array, part + 1, high);
		}
		
		return array;
	}
	
	public DatabaseUsersInformation[] sort(DatabaseUsersInformation[] array, String column) {
		this.column = column;
		
		array = quickSort(array, 0, array.length - 1);
		
		return array;
	}
	
}









