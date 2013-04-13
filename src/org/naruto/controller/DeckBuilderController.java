package org.naruto.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public ArrayList<Card> searchForMatches(HttpServletRequest req) throws SQLException{
		// create card object
		Card card = populateCardFromRequest(req);
		return Database.getInstance().searchForCardMatches(card);
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
		            return Integer.compare(turnCost1, turnCost2);
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

	public void saveDeck(HttpServletRequest req, HttpServletResponse resp) {
		// create file
		
	}
}
