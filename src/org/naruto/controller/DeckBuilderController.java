package org.naruto.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

import org.naruto.model.Card;
import org.naruto.model.Deck;
import org.naruto.model.persist.Database;

public class DeckBuilderController {
	private Deck deck;
	
	public Deck getDeck(){
		return deck;
	}
	
	public void setDeck(Deck deck){
		this.deck = deck;
	}
	
	public Deck addCardToMain(Card card, int quantity){
		for (int i = 0; i < quantity; i++){
			deck.addCardToMainDeck(card);
		}
		
		return deck;
	}
	
	public Deck addCardToSide(Card card, int quantity){
		for (int i = 0; i < quantity; i++){
			deck.addCardToSideDeck(card);
		}
		
		return deck;
	}
	
	public Deck addCardToReinforcement(Card card, int quantity){
		for (int i = 0; i < quantity; i++){
			deck.addCardToReinforcementDeck(card);
		}
		
		return deck;
	}
	
	public Deck removeCardFromMain(Card card, int quantity) {
		 for (int i = 0; i < quantity; i++){
			 deck.removeCardFromMainDeck(card);
		 }
		 
		 return deck;
	}
	
	public Deck removeCardFromSide(Card card, int quantity) {
		 for (int i = 0; i < quantity; i++){
			 deck.removeCardFromSideDeck(card);
		 }
		 
		 return deck;
	}
	
	public Deck removeCardFromReinforcement(Card card, int quantity) {
		 for (int i = 0; i < quantity; i++){
			 deck.removeCardFromReinforcementDeck(card);
		 }
		 
		 return deck;
	}
	
	public ArrayList<String> getDeckErrors(){
		if (deck.isBlockLegal()){
			return null;
		} else {
			return deck.getErrors();
		}
	}
	
	public ArrayList<Card> searchForMatches(HttpServletRequest req){
		try {
//			String cardName = req.getParameter("cardNameBox");
//			String cardNumber = req.getParameter("cardNumberBox");
//			
//			// Search for matching cards
//			if (!cardNumber.equals("") && !cardName.equals("")){
//	
//				// Search based on card number
//				ArrayList<Card> searchResults = new ArrayList<Card>();
//				searchResults.add(Database.getInstance().getCardByCardNumber(cardNumber));
//				
//				// Make sure the card name also matches
//				if (searchResults.get(0) != null && searchResults.get(0).getCardName().equals(cardName)){
//					return searchResults;
//				}	
//			} else if (!cardNumber.equals("")){
//				// Search based on card number
//				ArrayList<Card> searchResults = new ArrayList<Card>();
//				searchResults.add(Database.getInstance().getCardByCardNumber(cardNumber));
//				
//				if (searchResults.get(0) != null) {
//					return searchResults;
//				}
//			} else if (!cardName.equals("")) {
//				// Search based on card name
//				return Database.getInstance().getCardsByCardName(cardName);	
//			}
			
			// create card object
			Card card = populateCardFromRequest(req);
			return Database.getInstance().searchForCardMatches(card);
		} catch (SQLException e) {
				e.printStackTrace();
			
		}
		return null;
	}

	private Card populateCardFromRequest(HttpServletRequest req) {
		// Create card
		Card card = new Card();
		card.setCardName(req.getParameter("cardNameBox"));
		card.setCardNumber(req.getParameter("cardNumberBox"));
		card.addAttribute(req.getParameter("combatAttributeBox"));
		card.addCharacteristic(req.getParameter("characteristicBox"));
		card.setEffect(req.getParameter("effectBox"));
		if (!req.getParameter("elementBox").equals("NONE")){
			card.addElement(req.getParameter("elementBox"));
		} 
		if (!req.getParameter("turnCostBox").equals("NONE")){
			card.setTurnChakraCost(req.getParameter("turnCostBox"));
		} 
		if (!req.getParameter("handCostBox").equals("NONE")){
			card.setHandCost(Integer.parseInt(req.getParameter("handCostBox")));
		} 
		
		return card;
	}

	public void sortDeck() {
		Collections.sort(deck.getMainDeck(), new Comparator<Card>() {

	        public int compare(Card card1, Card card2) {	 
	        	// sort by card name
	            String cardName1 = ((Card) card1).getCardName();
	            String cardName2 = ((Card) card2).getCardName();
	            int nameComparison = cardName1.compareTo(cardName2);

	            if (nameComparison != 0) {
	               return nameComparison;
	            } else {
	            	// sort by card number
	               String cardNumber1 = ((Card) card1).getCardNumber();
	               String cardNumber2 = ((Card) card2).getCardNumber();
	               return cardNumber1.compareTo(cardNumber2);
	            }
	        }
		});	
		
		// sort by entrance cost
		Collections.sort(deck.getMainDeck(), new Comparator<Card>() {

	        public int compare(Card card1, Card card2) {	 
	        	if (card1.isJutsu() && card2.isJutsu()){
	        		return 0;
	        	} else if (card1.isJutsu()){
	        		return -1;
	        	} else if (card2.isJutsu()){
	        		return 1;
	        	} else {
		            int turnCost1 = Integer.parseInt(((Card) card1).getTurnChakraCost());
		            int turnCost2 = Integer.parseInt(((Card) card2).getTurnChakraCost());
		            return Integer.valueOf(turnCost1).compareTo(turnCost2);
	        	}
	        }
		});	
		
		//sort by card type
		ArrayList<Card> sortedMainDeck =  new ArrayList<Card>();
		for (Card card : deck.getMainDeck()){
			if (card.isNinja()){
				sortedMainDeck.add(card);
			}
		}
		
		for (Card card : deck.getMainDeck()){
			if (card.isJutsu()){
				sortedMainDeck.add(card);
			}
		}
		
		for (Card card : deck.getMainDeck()){
			if (card.isMission()){
				sortedMainDeck.add(card);
			}
		}
		
		for (Card card : deck.getMainDeck()){
			if (card.isClient()){
				sortedMainDeck.add(card);
			}
		}
		
		deck.setMainDeck(sortedMainDeck);
		
		Collections.sort(deck.getSideDeck(), new Comparator<Card>() {

	        public int compare(Card card1, Card card2) {	 
	        	// sort by card name
	            String cardName1 = ((Card) card1).getCardName();
	            String cardName2 = ((Card) card2).getCardName();
	            int nameComparison = cardName1.compareTo(cardName2);

	            if (nameComparison != 0) {
	               return nameComparison;
	            } else {
	            	// sort by card number
	               String cardNumber1 = ((Card) card1).getCardNumber();
	               String cardNumber2 = ((Card) card2).getCardNumber();
	               return cardNumber1.compareTo(cardNumber2);
	            }
	        }
		});	
				
		Collections.sort(deck.getReinforcementDeck(), new Comparator<Card>() {

	        public int compare(Card card1, Card card2) {	 
	        	// sort by card name
	            String cardName1 = ((Card) card1).getCardName();
	            String cardName2 = ((Card) card2).getCardName();
	            int nameComparison = cardName1.compareTo(cardName2);

	            if (nameComparison != 0) {
	               return nameComparison;
	            } else {
	            	// sort by card number
	               String cardNumber1 = ((Card) card1).getCardNumber();
	               String cardNumber2 = ((Card) card2).getCardNumber();
	               return cardNumber1.compareTo(cardNumber2);
	            }
	        }
		});
	}
}
