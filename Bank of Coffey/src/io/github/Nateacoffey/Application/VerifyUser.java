package io.github.Nateacoffey.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VerifyUser {
	ResultSet TestUsernamePassword(Statement databaseSQLStatement, String username, String password) {
		
		
		try {
			
			//returns user information from database matching username and password
			ResultSet databaseResults = databaseSQLStatement.executeQuery(
					"SELECT ADDRESS,"
					+ "USERNAME,"
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
			
			return databaseResults;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
