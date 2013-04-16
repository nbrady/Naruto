package org.naruto.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> mainDeck; // 0 is top of the deck, size() is the bottom
	private ArrayList<Card> sideDeck;
	private ArrayList<Card> reinforcementDeck;
	private ArrayList<String> errors; // this field is used to hold all the deck building rule violations
	
	// Creates an empty deck 
	public Deck(){
		mainDeck = new ArrayList<Card>();
		sideDeck = new ArrayList<Card>();
		reinforcementDeck = new ArrayList<Card>();
		errors = new ArrayList<String>();
	}
	
	public Deck(ArrayList<Card> mainDeck, ArrayList<Card> sideDeck, ArrayList<String> errors){
		this.mainDeck = mainDeck;
		this.sideDeck = sideDeck;
		this.errors = errors;
	}
	
	public ArrayList<Card> getMainDeck(){
		return mainDeck;
	}
	
	public void setMainDeck(ArrayList<Card> cards){
		this.mainDeck = cards;
	}
	
	public void setSideDeck(ArrayList<Card> sideDeck) {
		this.sideDeck = sideDeck;
	}

	public ArrayList<Card> getSideDeck() {
		return sideDeck;
	}

	public ArrayList<Card> getReinforcementDeck() {
		return reinforcementDeck;
	}

	public void setReinforcementDeck(ArrayList<Card> reinforcementDeck) {
		this.reinforcementDeck = reinforcementDeck;
	}

	public ArrayList<String> getErrors(){
		return errors;
	}
	
	public void setErrors(ArrayList<String> errors){
		this.errors = errors;
	}
		
	public boolean isMainDeckEmpty(){
		if (mainDeck.isEmpty()){
			return true;
		} else {
			return false;
		}	
	}
	
	public boolean isSideDeckEmpty(){
		if (sideDeck.isEmpty()){
			return true;
		} else {
			return false;
		}
	}
	
	public Card getCardFromMainDeck(int index){
		return mainDeck.get(index);
	}
	
	public Card getCardFromSideDeck(int index){
		return sideDeck.get(index);
	}
	
	public Card getCardFromReinforcementDeck(int index){
		return reinforcementDeck.get(index);
	}
	
	public void addCardToMainDeck(Card card){
		mainDeck.add(card);
	}
	
	public void removeCardFromMainDeck(Card card){
		mainDeck.remove(card);
	}
	
	public void addCardToSideDeck(Card card){
		sideDeck.add(card);
	}
	
	public void removeCardFromSideDeck(Card card){
		sideDeck.remove(card);
	}
	
	public void addCardToReinforcementDeck(Card card){
		reinforcementDeck.add(card);
	}
	
	public void removeCardFromReinforcementDeck(Card card){
		reinforcementDeck.remove(card);
	}
	
	public void shuffleMainDeck(){
		Collections.shuffle(mainDeck);
	}
		
	public boolean isBlockLegal(){
		errors.clear();
		
		// check to be sure main deck is exactly 50 cards
		if (mainDeck.size() != 50){
			errors.add("Main deck must be exactly 50 cards");
		}
		
		// check to be sure side deck is exactly 0 cards or 10 cards
		if (sideDeck.size() != 0 && sideDeck.size() != 10){
			errors.add("Side deck must be exactly 0 or 10 cards");
		}
		
		// check to be sure the reinforcement deck is 15 or less cards
		if (reinforcementDeck.size() > 15){
			errors.add("Reinforcement deck must be 15 or less cards");
		}
		
		// create combined deck
		@SuppressWarnings("unchecked")
		ArrayList<Card> combinedDeck = (ArrayList<Card>) mainDeck.clone();
		combinedDeck.addAll(sideDeck);
		
		// check to be sure no more than the maximum allowed copies are in the main deck and side deck combined
		ArrayList<String> processedCardNames = new ArrayList<String>();
		for (Card currentCard : combinedDeck){
			if(!processedCardNames.contains(currentCard.getCardName())){
				int occurences = 0;
				for(Card card : combinedDeck){
					if (card.getCardName().equals(currentCard.getCardName())){
						occurences++;
					}	
				}
				if (occurences > currentCard.getMaxBlockCopies()){
					errors.add("Deck contains more than " + currentCard.getMaxBlockCopies() + " copies of card with name: " + currentCard.getCardName());
				}
				processedCardNames.add(currentCard.getCardName());	
			}
		}
		
		// make sure the main deck and side deck do not contain any reinforcements
		for (Card card : mainDeck){
			if (card.getCharacteristics().contains("REINFORCEMENT")){
				errors.add("Reinforcement found in main deck: "+ card.getCardName());
			}
		}
		for (Card card : sideDeck){
			if (card.getCharacteristics().contains("REINFORCEMENT")){
				errors.add("Reinforcement found in side deck: "+ card.getCardName());
			}
		}
		
		// make sure the reinforcement deck contains only reinforcements
		for (Card card : reinforcementDeck){
			if (!card.getCharacteristics().contains("REINFORCEMENT")){
				errors.add("Non-reinforcement found in reinforcement deck: "+ card.getCardName());
			}
		}
			
		if (errors.isEmpty()){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isUnlimitedLegal(){
		// TODO:
		return false;
	}
	
	public ArrayList<Card> searchCardByName(String cardName){
		ArrayList<Card> matches = new ArrayList<Card>();
		for(Card card: mainDeck){
			if (card.getCardName().equals(cardName)){
				matches.add(card);
			}
		}
		return matches;
	}

	public void printErrors(){
		for(String error : errors){
			System.out.println(error);
		}
	}
	
	public void printDeck(){
		System.out.println("Main Deck:");
		for(Card card: mainDeck){
			System.out.println(card.toString());
		}
		
		System.out.println("Side Deck:");
		for(Card card : sideDeck){
			System.out.println(card.toString());
		}
		
		System.out.println("Reinforcement Deck:");
		for(Card card : reinforcementDeck){
			System.out.println(card.toString());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result
				+ ((mainDeck == null) ? 0 : mainDeck.hashCode());
		result = prime
				* result
				+ ((reinforcementDeck == null) ? 0 : reinforcementDeck
						.hashCode());
		result = prime * result
				+ ((sideDeck == null) ? 0 : sideDeck.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Deck))
			return false;
		Deck other = (Deck) obj;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (mainDeck == null) {
			if (other.mainDeck != null)
				return false;
		} else if (!mainDeck.equals(other.mainDeck))
			return false;
		if (reinforcementDeck == null) {
			if (other.reinforcementDeck != null)
				return false;
		} else if (!reinforcementDeck.equals(other.reinforcementDeck))
			return false;
		if (sideDeck == null) {
			if (other.sideDeck != null)
				return false;
		} else if (!sideDeck.equals(other.sideDeck))
			return false;
		return true;
	}
	
	public int getNumberInMainDeck(Card card){
		int occurences = 0;
		for (Card c : mainDeck){
			if (c.equals(card)){
				occurences++;
			}
		}
		return occurences;
	}
	
	public int getNumberInSideDeck(Card card){
		int occurences = 0;
		for (Card c : sideDeck){
			if (c.equals(card)){
				occurences++;
			}
		}
		return occurences;
	}
	
	public int getNumberInReinforcementDeck(Card card){
		int occurences = 0;
		for (Card c : reinforcementDeck){
			if (c.equals(card)){
				occurences++;
			}
		}
		return occurences;
	}
}
