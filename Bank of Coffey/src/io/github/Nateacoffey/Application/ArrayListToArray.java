package io.github.Nateacoffey.Application;

import java.util.List;

public class ArrayListToArray {
	
	public DatabaseUsersInformation[] toArray(List<DatabaseUsersInformation> dbList) {
		
		int size = dbList.size();
		
		DatabaseUsersInformation[] dbArray = new DatabaseUsersInformation[dbList.size()];
		
		for(int i = 0; i < size; i++) {
			dbArray[i] = dbList.get(i);
		}
		
		
		return dbArray;
	}
}
