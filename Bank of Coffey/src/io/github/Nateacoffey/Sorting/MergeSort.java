package io.github.Nateacoffey.Sorting;

import io.github.Nateacoffey.Admin.DatabaseUsersInformation;

public class MergeSort {
	
	private String column;
	
	public int switchComparison(DatabaseUsersInformation[] leftArray, int i, DatabaseUsersInformation[] rightArray, int j) {
		
		Integer intWrapI;
		Integer intWrapJ;
		
		int compare = 0;
		
		//compare depending on the column sort
		switch(column) {
			case "Amount of Accounts":
				intWrapI = new Integer(leftArray[i].getAmountOfAccounts());
				intWrapJ = new Integer(rightArray[j].getAmountOfAccounts());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
			case "First Name":
				compare = leftArray[i].getFirstName().compareTo(rightArray[j].getFirstName());
				break;
			case "Latest Login":
				compare = leftArray[i].getLatestLogin().compareTo(rightArray[j].getLatestLogin()) * -1;//reverse order for date
				break;
			case "State":
				compare = leftArray[i].getState().compareTo(rightArray[j].getState());
				break;
			default://case Zip Code
				intWrapI = new Integer(leftArray[i].getZipCode());
				intWrapJ = new Integer(rightArray[j].getZipCode());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
		};
		
		return compare;
	}
	
	private DatabaseUsersInformation[] merge(DatabaseUsersInformation[] array, int low, int middle, int high) {
		
		//gets the sizes of the two sub-arrays
		int leftArrayLength = middle - low + 1;
		int rightArrayLength = high - middle;
		
		DatabaseUsersInformation[] leftArrayCopy = new DatabaseUsersInformation[leftArrayLength];
		DatabaseUsersInformation[] rightArrayCopy = new DatabaseUsersInformation[rightArrayLength];
		
		//copies arrays to the temporary arrays
		for(int i = 0; i < leftArrayLength; ++i) {
			leftArrayCopy[i] = array[low + i];
		}
		
		for(int i = 0; i < rightArrayLength; ++i) {
			rightArrayCopy[i] = array[middle + 1 + i];
		}
		
		//array indexes
		int leftArrayIndex = 0, rightArrayIndex = 0;
		
		//compares copied array values and adds the smaller object value into the array
		int lowCounter = low;
		while(leftArrayIndex < leftArrayLength && rightArrayIndex < rightArrayLength) {
			if(switchComparison(leftArrayCopy, leftArrayIndex, rightArrayCopy, rightArrayIndex) <= 0) {
				array[lowCounter] = leftArrayCopy[leftArrayIndex];
				leftArrayIndex++;
			}else {
				array[lowCounter] = rightArrayCopy[rightArrayIndex];
				rightArrayIndex++;
			}
			lowCounter++;
		}
		
		//copies any elements leftover
		while(leftArrayIndex < leftArrayLength) {
			array[lowCounter] = leftArrayCopy[leftArrayIndex];
			leftArrayIndex++;
			lowCounter++;
		}
		
		//copies any elements leftover
		while(rightArrayIndex < rightArrayLength) {
			array[lowCounter] = rightArrayCopy[rightArrayIndex];
			rightArrayIndex++;
			lowCounter++;
		}
		
		
		return array;
	}
	
	private DatabaseUsersInformation[] mergeSort(DatabaseUsersInformation[] array, int low, int high) {
		
		if(low < high) {
			int middle = (low + high) / 2;
			
			//sorts the left and right sub-arrays
			array = mergeSort(array, low, middle);
			array = mergeSort(array, middle + 1, high);
			
			//merges the two sub-arrays
			array = merge(array, low, middle, high);
		}
		
		return array;
	}
	
	public DatabaseUsersInformation[] sort(DatabaseUsersInformation[] array, String column) {
		
		this.column = column;
		
		array = mergeSort(array, 0, array.length - 1);
		
		return array;
	}
}
