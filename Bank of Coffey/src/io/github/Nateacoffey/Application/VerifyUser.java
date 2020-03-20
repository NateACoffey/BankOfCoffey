package io.github.Nateacoffey.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VerifyUser {
	ResultSet TestUsernamePassword(Statement st, String username, String password) {
		
		try {
			
			
			ResultSet rs = st.executeQuery(
					"SELECT ADDRESS,"
					+ "CITY,"
					+ "STATE,"
					+ "ZIP_CODE,"
					+ "PHONE_NUMBER,"
					+ "ACCOUNT_SERIAL,"
					+ "AMOUNT_OF_ACCOUNTS,"
					+ "FIRST_NAME,"
					+ "LAST_NAME "
					+ ""
					+ "FROM USER_INFORMATION "
					+ ""
					+ " WHERE USERNAME = '" + username
					+ "' AND PASSWORD = '" + password + "'"
					
					);
			
			return rs;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
