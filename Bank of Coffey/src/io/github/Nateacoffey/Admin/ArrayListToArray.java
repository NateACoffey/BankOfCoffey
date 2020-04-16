package io.github.Nateacoffey.Admin;

import java.util.List;

public class ArrayListToArray {
	
	public DatabaseUsersInformation[] toArray(List<DatabaseUsersInformation> dbList) {
		
		int size = dbList.size();
		
		//create array based on the size of the List
		DatabaseUsersInformation[] databaseArray = new DatabaseUsersInformation[size];
		
		for(int i = 0; i < size; i++) {
			databaseArray[i] = dbList.get(i);
		}
		
		
		return databaseArray;
	}
}
