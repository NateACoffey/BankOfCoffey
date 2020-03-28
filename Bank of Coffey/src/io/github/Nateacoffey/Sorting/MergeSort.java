package io.github.Nateacoffey.Sorting;

import io.github.Nateacoffey.Application.DatabaseUsersInformation;

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
			case "Zip Code":
				intWrapI = new Integer(leftArray[i].getZipCode());
				intWrapJ = new Integer(rightArray[j].getZipCode());
				
				compare = intWrapI.compareTo(intWrapJ);
				break;
		};
		
		return compare;
	}
	
	private DatabaseUsersInformation[] merge(DatabaseUsersInformation[] array, int l, int m, int r) {
		
		int n1 = m - l + 1;
		int n2 = r - m;
		
		DatabaseUsersInformation[] L = new DatabaseUsersInformation[n1];
		DatabaseUsersInformation[] R = new DatabaseUsersInformation[n2];
		
		for(int i = 0; i < n1; ++i) {
			L[i] = array[l + i];
		}
		
		for(int i = 0; i < n2; ++i) {
			R[i] = array[m + 1 + i];
		}
		
		
		int i = 0, j = 0;
		
		int k = l;
		while(i < n1 && j < n2) {
			if(switchComparison(L, i, R, j) <= 0) {
				array[k] = L[i];
				i++;
			}else {
				array[k] = R[j];
				j++;
			}
			k++;
		}
		
		while(i < n1) {
			array[k] = L[i];
			i++;
			k++;
		}
		
		while(j < n2) {
			array[k] = R[j];
			j++;
			k++;
		}
		
		
		return array;
	}
	
	private DatabaseUsersInformation[] mergeSort(DatabaseUsersInformation[] array, int l, int r) {
		
		if(l < r) {
			int middle = (l + r) / 2;
			
			array = mergeSort(array, l, middle);
			array = mergeSort(array, middle + 1, r);
			
			
			array = merge(array, l, middle, r);
		}
		
		return array;
	}
	
	public DatabaseUsersInformation[] sort(DatabaseUsersInformation[] array, String column) {
		
		this.column = column;
		
		array = mergeSort(array, 0, array.length - 1);
		
		return array;
	}
}
