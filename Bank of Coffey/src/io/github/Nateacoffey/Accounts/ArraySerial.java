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
	
	
	
	public void serial(String username, Statement databaseSQLStatement) {
		try {
			//converts Array to byte[] stream
			ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
			//do not delete
			ObjectOutput objectOut = new ObjectOutputStream(byteArrayOutput);
			objectOut.writeObject(UserInformation.arrayOfAccounts);
			
			byte[] byteArray = byteArrayOutput.toByteArray();
			
			String serializedObject = Base64.getEncoder().encodeToString(byteArray);
			
			//updates the array for user
			databaseSQLStatement.executeUpdate(
					"UPDATE USER_INFORMATION "
					+ ""
					+ "SET ACCOUNT_SERIAL = '" + serializedObject
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
			
			//converts the byte array back into the Object array
			ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(byteArray);
			ObjectInput objectIn = new ObjectInputStream(byteArrayInput);
			
			arrayOfAccounts = (UserAccountInformation[]) objectIn.readObject();
			
			
			return arrayOfAccounts;
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
