package model;

import java.util.ArrayList;

public class Card {
	private int id;
	private int set;
	private String cardName;
	private String cardNumber; // cardNumber may contain letters
	private ArrayList<String> elements; // TODO: Create an element enum
	private int turnChakraCost;
	private int handCost;
	private ArrayList<String> characteristics; // TODO: possible enum for characteristics
	private String attribute;
	private String rarity; // TODO: create an enum
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSet() {
		return set;
	}
	
	public void setSet(int set) {
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
	
	public ArrayList<String> getElements() {
		return elements;
	}
	
	public void setElements(ArrayList<String> elements) {
		this.elements = elements;
	}
	
	public int getTurnChakraCost() {
		return turnChakraCost;
	}
	
	public void setTurnChakraCost(int turnChakraCost) {
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
	
	public String getAttribute() {
		return attribute;
	}
	
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	public String getRarity() {
		return rarity;
	}
	
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
}
