package io.github.Nateacoffey.Accounts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class ArraySerial {
	
	
	
	public void serial(UserAccountInformation[] arrayOfAccounts, String username, Statement st) {
		try {
			//converts Array to byte[] stream
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(arrayOfAccounts);
			byte[] byteArray = bos.toByteArray();
			
			String test = Base64.getEncoder().encodeToString(byteArray);
			
			//updates the array for user
			st.executeUpdate(
					"UPDATE USER_INFORMATION "
					+ ""
					+ "SET ACCOUNT_SERIAL = '" + test
					+ ""
					+ "' WHERE USERNAME = '" + username
					+ "'"
			);
			
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public UserAccountInformation[] deserial(String serial) {
		
		UserAccountInformation[] arrayOfAccounts;
		
		
		try {
			byte[] byteArray = Base64.getDecoder().decode(serial);
			
			
			ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
			ObjectInput in = new ObjectInputStream(bis);
			
			arrayOfAccounts = (UserAccountInformation[]) in.readObject();
			
			
			return arrayOfAccounts;
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
