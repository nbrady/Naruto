package org.naruto.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> mainDeck; // 0 is top of the deck, size() is the bottom
	private ArrayList<Card> sideDeck;
	private ArrayList<String> errors; // this field is used to hold all the deck building rule violations
	
	// Creates an empty deck 
	public Deck(){
		mainDeck = new ArrayList<Card>();
		sideDeck = new ArrayList<Card>();
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
	
	public void shuffleMainDeck(){
		Collections.shuffle(mainDeck);
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == null || !(o instanceof Deck)) {
	      return false;
	    }
	    Deck other = (Deck) o;
	    if (mainDeck.equals(other.mainDeck) &&
	    	sideDeck.equals(other.sideDeck) &&
	    	errors.equals(other.errors)) { 
	      return true;
	    } else {
	      return false;
	    }
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
		
		// create combined deck
		@SuppressWarnings("unchecked")
		ArrayList<Card> combinedDeck = (ArrayList<Card>) mainDeck.clone();
		combinedDeck.addAll(sideDeck);
		
		// check to be sure no more than 3 copies of a card with the same name are in the main deck and side deck combined
		ArrayList<String> processedCardNames = new ArrayList<String>();
		for (Card currentCard : combinedDeck){
			if(!processedCardNames.contains(currentCard.getCardName())){
				int occurences = 0;
				for(Card card : combinedDeck){
					if (card.getCardName().equals(currentCard.getCardName())){
						occurences++;
					}	
				}
				if (occurences > 3){
					errors.add("Deck contains more than 3 copies of card with name: " + currentCard.getCardName());
				}
				processedCardNames.add(currentCard.getCardName());	
			}
		}
		
		// TODO: check to be sure no cards that are not block legal are played
		
		// TODO: check rogue list
		
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
	}
}
