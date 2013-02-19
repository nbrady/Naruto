package org.naruto.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Card {
	
	// Fields
	private int id;
	private double set; // promotional sets are denoted with decimals; Ex. the promotional cards that were released between set 17 and set 18 are denoted as set 17.5
	private String cardName;
	private String cardNumber; // cardNumber may contain letters; Ex. N-007
	private ArrayList<Element> elements; 
	private String turnChakraCost; // for jutsu cards the cost is denoted with letters; Ex. WWW1 would denote a jutsu that costs 3 wind and 1 generic chakra 
	private int handCost;
	private ArrayList<String> characteristics; 
	private ArrayList<String> attributes;
	private Rarity rarity; 
	// TODO : add images
	
	public Card(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSet() {
		return set;
	}

	public void setSet(double set) {
		this.set = set;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public ArrayList<Element> getElements() {
		return elements;
	}

	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}

	public String getTurnChakraCost() {
		return turnChakraCost;
	}

	public void setTurnChakraCost(String turnChakraCost) {
		this.turnChakraCost = turnChakraCost;
	}

	public int getHandCost() {
		return handCost;
	}

	public void setHandCost(int handCost) {
		this.handCost = handCost;
	}

	public ArrayList<String> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(ArrayList<String> characteristics) {
		this.characteristics = characteristics;
	}

	public ArrayList<String> getAttribute() {
		return attributes;
	}

	public void setAttribute(ArrayList<String> attribute) {
		this.attributes = attribute;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	public void loadFrom(ResultSet resultSet) throws SQLException {
		loadFrom(resultSet, 1);
	}
	
	
	// Create a Card object from a ResultSet beginning at the specified index
	public void loadFrom(ResultSet resultSet, int index) throws SQLException {
		id = resultSet.getInt(index++);
		set = resultSet.getDouble(index++);
		cardName = resultSet.getString(index++);
		cardNumber = resultSet.getString(index++);
		
		// Parse the element string into an ArrayList
		String elementString = resultSet.getString(index++);
		for (Element element : Element.values()){
			if (elementString.contains(element.toString())){
				elements.add(element);
			}
		}
		
		turnChakraCost = resultSet.getString(index++);
		handCost = resultSet.getInt(index++);
		
		// Parse the characteristics into an ArrayList
		String characteristicString[] = resultSet.getString(index++).split(" ");
		for (String characteristic : characteristicString){
			characteristics.add(characteristic);
		}
		
		// Parse the attributes string into an ArrayList
		String attributeString[] = resultSet.getString(index++).split("/");
		for (String attribute : attributeString){
			attributes.add(attribute);
		}
		
		rarity = Rarity.valueOf(resultSet.getString(index++));	
	}
	
	public void storeTo(PreparedStatement stmt) throws SQLException {
		storeTo(stmt, 1);
	}
	
	// Set the parameters of a PreparedStatement beginning at the specified index
	public void storeTo(PreparedStatement stmt, int index) throws SQLException {
		stmt.setInt(index++, id);
		stmt.setDouble(index++, set);
		stmt.setString(index++, cardName);
		stmt.setString(index++, cardNumber);
		
		// Create a properly formated string of all the elements separated by "/"
		String elementString = "";
		for (Element element : elements){
			elementString = elementString + element.toString() + "/";
		}
		if (elementString.endsWith("/")){
			elementString.substring(0, elementString.length() - 1);
		}
		stmt.setString(index++, elementString);
		
		stmt.setString(index++, turnChakraCost);
		stmt.setInt(index++, handCost);
		
		// Create a properly formated string of all the elements separated by " "
		String characteristicString = "";
		for (String characteristic : characteristics){
			characteristicString = characteristicString + characteristic + " ";
		}
		characteristicString = characteristicString.trim();
		stmt.setString(index++, characteristicString);
		
		// Create a properly formated string of all the attributes separated by " "
		String attributeString = "";
		for (String attribute : attributes){
			attributeString = attributeString + attribute + " ";
		}
		attributeString = attributeString.trim();
		stmt.setString(index++, attributeString);
	
		stmt.setString(index++, rarity.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result
				+ ((cardName == null) ? 0 : cardName.hashCode());
		result = prime * result
				+ ((cardNumber == null) ? 0 : cardNumber.hashCode());
		result = prime * result
				+ ((characteristics == null) ? 0 : characteristics.hashCode());
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + handCost;
		result = prime * result + id;
		result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
		long temp;
		temp = Double.doubleToLongBits(set);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((turnChakraCost == null) ? 0 : turnChakraCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (cardNumber == null) {
			if (other.cardNumber != null)
				return false;
		} else if (!cardNumber.equals(other.cardNumber))
			return false;
		if (characteristics == null) {
			if (other.characteristics != null)
				return false;
		} else if (!characteristics.equals(other.characteristics))
			return false;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (handCost != other.handCost)
			return false;
		if (id != other.id)
			return false;
		if (rarity != other.rarity)
			return false;
		if (Double.doubleToLongBits(set) != Double.doubleToLongBits(other.set))
			return false;
		if (turnChakraCost == null) {
			if (other.turnChakraCost != null)
				return false;
		} else if (!turnChakraCost.equals(other.turnChakraCost))
			return false;
		return true;
	}

}
