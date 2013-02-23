package org.naruto.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		
		try{
			stmt = conn.prepareStatement("SELECT * FROM cards WHERE id=?");
			stmt.setInt(1, id);
			
			System.out.println(stmt);
			resultSet = stmt.executeQuery();
			if (resultSet.next()){
				Card card = new Card();
				card.loadFrom(resultSet);
				return card;
			} else {
				// No card was found with the given id
				return null;
			}	
		} finally{
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}
	
	public Card getCardByCardNumber(String cardNumber) throws SQLException{
		Connection conn = DBUtil.getThreadLocalConnection();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try{
			stmt = conn.prepareStatement("SELECT * FROM cards WHERE card_number=?");
			stmt.setString(1, cardNumber);
			
			System.out.println(stmt);
			resultSet = stmt.executeQuery();
			if (resultSet.next()){
				Card card = new Card();
				card.loadFrom(resultSet);
				return card;
			} else {
				// No card was found with the given cardNumber
				return null;
			}	
		} finally{
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}

	public ArrayList<Card> getCardsByCardName(String cardName) throws SQLException {
		Connection conn = DBUtil.getThreadLocalConnection();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try{
			stmt = conn.prepareStatement("SELECT * FROM cards WHERE card_name=?");
			stmt.setString(1, cardName);
			
			System.out.println(stmt);
			resultSet = stmt.executeQuery();
			
			ArrayList<Card> results = new ArrayList<Card>();
			while (resultSet.next()){
				Card card = new Card();
				card.loadFrom(resultSet);
				results.add(card);
			}
			
			if (results.isEmpty()){
				return null;
			} else {
				return results;
			}
		} finally{
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}
	
}
