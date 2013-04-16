package org.naruto.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
		
		try {
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
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}
	
	public Card getCardByCardNumber(String cardNumber) throws SQLException{
		Connection conn = DBUtil.getThreadLocalConnection();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
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
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}

	public ArrayList<Card> getCardsByCardName(String cardName) throws SQLException {
		Connection conn = DBUtil.getThreadLocalConnection();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
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
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}

	public Collection<String> suggestCardNames(String term) throws SQLException {
		Connection conn = DBUtil.getThreadLocalConnection();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = conn.prepareStatement("SELECT distinct card_name FROM cards WHERE card_name LIKE ?");
			stmt.setString(1, term + "%");
			
			resultSet = stmt.executeQuery();
			
			List<String> result = new ArrayList<String>();
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
				                       
			return result;
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(conn);
		}
	}

	public ArrayList<Card> searchForCardMatches(Card card, String cardType) throws SQLException {
		Connection conn = DBUtil.getThreadLocalConnection();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			// Determine what the query should be for handcost and set
			// Note: this is necessary because there is no wildcard support for integers and doubles
			String handCostQuery = "";
			if (card.getHandCost() == -1){
				handCostQuery = ">= 0";
			} else {
				handCostQuery = "= " + card.getHandCost();
			}
			
			stmt = conn.prepareStatement("SELECT * FROM cards WHERE card_name LIKE ? and card_number LIKE ? and " +
					"element LIKE ? and turn_chakra_cost LIKE ? and characteristics LIKE ? and effect LIKE ? and " +
					"attribute LIKE ? and rarity LIKE ? and hand_cost " + handCostQuery);
			card.storeToWithWildcards(stmt);
			
			System.out.println(stmt);
			resultSet = stmt.executeQuery();
			
			ArrayList<Card> results = new ArrayList<Card>();
			while (resultSet.next()){
				Card result = new Card();
				result.loadFrom(resultSet);
				results.add(result);
			}
			
			// Only show results of the card type the user selected
			results = removeOtherCardTypes(results, cardType);
			
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

	private ArrayList<Card> removeOtherCardTypes(ArrayList<Card> results, String cardType) {
		if (cardType.equalsIgnoreCase("Ninja")){
			Iterator<Card> iterator = results.iterator();
			while (iterator.hasNext()){
				if (!iterator.next().isNinja()){
					System.out.println("Gets here");
					iterator.remove();
				}
			}
		} else if (cardType.equalsIgnoreCase("Mission")){
			Iterator<Card> iterator = results.iterator();
			while (iterator.hasNext()){
				if (!iterator.next().isMission()){
					iterator.remove();
				}
			}
		} else if (cardType.equalsIgnoreCase("Jutsu")){
			Iterator<Card> iterator = results.iterator();
			while (iterator.hasNext()){
				if (!iterator.next().isJutsu()){
					iterator.remove();
				}
			}
		} else if (cardType.equalsIgnoreCase("Client")){
			Iterator<Card> iterator = results.iterator();
			while (iterator.hasNext()){
				if (!iterator.next().isJutsu()){
					iterator.remove();
				}
			}
		}
		
		return results;
	}
	
}
