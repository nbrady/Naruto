package org.naruto.controller;

import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public ArrayList<String> getDeckErrors(){
		if (deck.isBlockLegal()){
			return null;
		} else {
			return deck.getErrors();
		}
	}
	
	public ArrayList<Card> searchForMatches(HttpServletRequest req){
		try {
			String cardName = req.getParameter("cardNameBox");
			String cardNumber = req.getParameter("cardNumberBox");
			
			// Search for matching cards
			if (!cardNumber.equals("") && !cardName.equals("")){
	
				// Search based on card number
				ArrayList<Card> searchResults = new ArrayList<Card>();
				searchResults.add(Database.getInstance().getCardByCardNumber(cardNumber));
				
				// Make sure the card name also matches
				if (searchResults.get(0) != null && searchResults.get(0).getCardName().equals(cardName)){
					return searchResults;
				}	
			} else if (!cardNumber.equals("")){
				// Search based on card number
				ArrayList<Card> searchResults = new ArrayList<Card>();
				searchResults.add(Database.getInstance().getCardByCardNumber(cardNumber));
				
				if (searchResults.get(0) != null) {
					return searchResults;
				}
			} else if (!cardName.equals("")) {
				// Search based on card name
				return Database.getInstance().getCardsByCardName(cardName);	
			}
		} catch (SQLException e) {
				e.printStackTrace();
			
		}
		return null;
	}

}
