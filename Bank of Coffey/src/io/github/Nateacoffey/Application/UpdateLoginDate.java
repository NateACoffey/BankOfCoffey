package io.github.Nateacoffey.Application;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateLoginDate {
	
	public void updateDatabaseLogin(String username, String password, Statement databaseSQLStatement) throws SQLException {
		//Creates a string based on the current date
				String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY/MM/dd"));
				
				databaseSQLStatement.executeUpdate(
						"UPDATE USER_INFORMATION "
						+ ""
						+ "SET LAST_LOGIN = '" + formatDateTime
						+ ""
						+ "' WHERE USERNAME = '" + username
						+ "' AND PASSWORD = '" + password + "'"
				);
				
	}
}
