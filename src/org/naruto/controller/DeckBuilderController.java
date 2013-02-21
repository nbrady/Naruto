package org.naruto.controller;

import java.util.ArrayList;

import org.naruto.model.Card;
import org.naruto.model.Deck;

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
	
	public ArrayList<String> getDeckErrors(){
		if (deck.isBlockLegal()){
			return null;
		} else {
			return deck.getErrors();
		}
	}
}
