package org.naruto.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBUtil {
	
	static {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("mysql JDBC driver not available?");
			}
	}
	private static final String JDBC_URL = "jdbc:mysql://localhost/naruto?user=root&password=root&autoReconnect=true"; 

	
	private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<Connection>();
	
	public static Connection getThreadLocalConnection() throws SQLException {
		
		Connection conn = threadLocalConnection.get();

		boolean haveValidConnection = false;
		
		while (!haveValidConnection) {
			if (conn == null) {
				// TODO: use a connection pool
				conn = DriverManager.getConnection(JDBC_URL);
				threadLocalConnection.set(conn);
				haveValidConnection = true; // a new connection should certainly be valid
			} else {
				// Attempt to ensure that the connection is still alive.
				// Connections time out if idle for too long.
				
				com.mysql.jdbc.Connection mysqlConn = (com.mysql.jdbc.Connection) conn;
				try {
					mysqlConn.ping();
					haveValidConnection = true;
				} catch (SQLException e) {
					// Connection is invalid: purge old (no longer valid) connection and try reconnecting
					threadLocalConnection.set(null);
					conn = null;
				}
			}
		}
		
		return conn;
	}
	
	public static void releaseThreadLocalConnection() {
		Connection conn = threadLocalConnection.get();
		if (conn != null) {
			DBUtil.closeQuietly(conn);
			threadLocalConnection.set(null);
		}
	}

	public static void closeQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				// FIXME: log this!!!
			}
		}
	}

	public static void closeQuietly(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				// FIXME: log this!!!
			}
		}
	}

	public static void closeQuietly(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				// FIXME: log this!!!
			}
		}
	}

	/**
	 * Run a query or update without wrapping it in a transaction.
	 * 
	 * @param runnable the query or update to run
	 * @throws SQLException
	 */
	public static void doNoTransaction(DatabaseRunnable runnable) throws SQLException {
		runnable.run(DBUtil.getThreadLocalConnection());
	}
	
	/**
	 * Run a query or update, wrapping it in a transaction.
	 * 
	 * @param runnable the query or update to run in a transaction
	 * @throws SQLException
	 */
	public static void doTransaction(DatabaseRunnable runnable) throws SQLException {
		Connection conn = DBUtil.getThreadLocalConnection();

		conn.setAutoCommit(false);
		
		int attempts = 0;
		final int MAX_ATTEMPTS = 10;
		SQLException exception = null;
		boolean success = false;

		try {
			//
			// Do the update in the scope of a transaction.
			// If a commit fails due to deadlock, retry until
			// the max retry count has been exceeded.
			//
			while (!success && attempts < MAX_ATTEMPTS) {
				boolean committed = false;
				attempts++;
				try {
					
					runnable.run(conn);
					conn.commit();
					committed = true;
					
					success = true;
				} catch (SQLException e) {
					if (!committed) {
						// Roll back the partially-completed work.
						conn.rollback();
					}
					
					exception = e;
					String sqlState = e.getSQLState();
					if (sqlState != null && sqlState.equals("40001")) {
						// Deadlock: see 
						// http://dev.mysql.com/doc/refman/5.0/en/connector-j-usagenotes-troubleshooting.html
					} else {
						// some other kind of exception
						throw exception;
					}
				}
			}
			
			if (!success) {
				throw exception;
			}
		
		} finally {
			conn.setAutoCommit(true);
		}

	}
}
