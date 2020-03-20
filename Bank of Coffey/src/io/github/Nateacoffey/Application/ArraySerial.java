package io.github.Nateacoffey.Application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArraySerial {
	
	
	
	public void serial(UserAccountInformation[] arrayOfAccounts, String username, Statement st) {
		try {
			
			/*Successfully serializes to .txt file:
			
			FileOutputStream fileOut = new FileOutputStream("testserial.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(arrayOfAccounts);
			out.close();
			fileOut.close();
			
			*/
			
			
			
			
			st.executeUpdate(
					"UPDATE USER_INFORMATION "
					+ ""
					+ "SET ACCOUNT_SERIAL = '" + ""//TODO need variable for writing
					+ ""
					+ "' WHERE USERNAME = '" + username
					+ "'"
			);
			
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public UserAccountInformation[] deserial(String serial) {
		
		UserAccountInformation[] arrayOfAccounts;
		
		
		try {
			FileInputStream fileIn = new FileInputStream(serial);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			arrayOfAccounts = (UserAccountInformation[]) in.readObject();
			in.close();
			fileIn.close();
			
			return arrayOfAccounts;
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
