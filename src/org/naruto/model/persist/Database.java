package org.naruto.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.naruto.model.Card;

public class Database {
	
	private static final Database theInstance = new Database();

	public static Database getInstance() {
		return theInstance;
	}
	
	public Database(){
		
	}
	
	public Card getCardById(int id) throws SQLException{
		Connection conn = DBUtil.getThreadLocalConnection();
  
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		return null;
	}
}
