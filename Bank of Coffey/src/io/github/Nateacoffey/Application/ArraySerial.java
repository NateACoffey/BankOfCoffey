package io.github.Nateacoffey.Application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArraySerial {
	
	
	
	public void serial(UserAccountInformation[] arrayOfAccounts, String username, Statement st) {
		try {
			//converts Array to byte[] stream
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(arrayOfAccounts);
			out.flush();
			
			String test = bos.toString();
			
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
			
			byte[] b = serial.getBytes(StandardCharsets.UTF_8);
			
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
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
