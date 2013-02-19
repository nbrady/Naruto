package org.naruto.model.persist;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseRunnable {
	/**
	 * Run the database query or update.
	 * 
	 * @param conn  the Connection to use
	 * @throws SQLException
	 */
	public void run(Connection conn) throws SQLException;
	
	/**
	 * @return a description of the query or update (for diagnostic purposes)
	 */
	public String getDescription();

}
