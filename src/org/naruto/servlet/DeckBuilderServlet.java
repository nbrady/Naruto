package org.naruto.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naruto.controller.DeckBuilderController;
import org.naruto.model.Card;
import org.naruto.model.Deck;
import org.naruto.model.Element;
import org.naruto.model.Rarity;
import org.naruto.model.persist.Database;

public class DeckBuilderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Deck deck = (Deck) req.getSession().getAttribute("deck");
		if (deck == null){
			// No deck found, create a new deck
			deck = new Deck();			
		}
		
		// Initialize controller
		DeckBuilderController controller = new DeckBuilderController();
		controller.setDeck(deck);
		
		// Sort the deck
		controller.sortDeck();		
		
		// prepare main deck to be displayed in rows of 10
		Deck tempDeck = deck;
		if (!tempDeck.isMainDeckEmpty()){
			ArrayList<ArrayList<Card>> mainDeck = new ArrayList<ArrayList<Card>>();
			ArrayList<Card> temp = new ArrayList<Card>();
			int j = 0;
			for (int i = 0; i < tempDeck.getMainDeck().size(); i++){
				if (j == 10){
					mainDeck.add(temp);
					temp = new ArrayList<Card>();
					j = 0;
				}
				temp.add(deck.getMainDeck().get(i));
				j++;
			}
			mainDeck.add(temp);
			
			req.getSession().setAttribute("mainDeck", mainDeck);
		}
		
		// set other attributes
		req.getSession().setAttribute("deck", deck);
		ArrayList<Element> elementChoices = new ArrayList<Element>(Arrays.asList(Element.values()));
		req.setAttribute("elementChoices", elementChoices);
		ArrayList<Rarity> rarityChoices = new ArrayList<Rarity>(Arrays.asList(Rarity.values()));
		req.setAttribute("rarityChoices", rarityChoices);
		
		req.getRequestDispatcher("/view/deckBuilder.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// Get current deck 
		Deck deck = (Deck) req.getSession().getAttribute("deck");
		if (deck == null){
			// No deck found, create a new empty deck
			deck = new Deck();
		}
		
		// Initialize controller
		DeckBuilderController controller = new DeckBuilderController();
		controller.setDeck(deck);
					
	    // Form submission
		if (req.getParameter("submitted") != null) { 
			// Parse fields
			String action = req.getParameter("action");
			int requestId = Integer.parseInt(req.getParameter("requestId"));
						
			int quantity = 1; // Default quantity is 1
			try {
				quantity = Integer.parseInt(req.getParameter("quantityBox" + requestId));
			} catch (NumberFormatException e){
				// Quantity is not required
			}
			
			if (action.equals("addCardToMain")){
				// Add card to main deck
				try {
					Card card = Database.getInstance().getCardById(requestId);
					controller.addCardToMain(card, quantity);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
			
			else if (action.equals("addCardToSide")){
				// Add card to side deck
				try {
					Card card = Database.getInstance().getCardById(requestId);
					controller.addCardToSide(card, quantity);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			} 
			
			else if (action.equals("addCardToReinforcement")){
				// Add card to reinforcement deck
				try {
					Card card = Database.getInstance().getCardById(requestId);
					controller.addCardToReinforcement(card, quantity);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			} 
			
			else if (action.equals("removeCardFromMain")){
				// Remove card from main deck
				try {
					Card card = Database.getInstance().getCardById(requestId);					
					controller.removeCardFromMain(card, 1);
				} catch (SQLException e){
					e.printStackTrace();
				}
			} 
			
			else if (action.equals("removeCardFromSide")){
				// Remove card from side deck
				try {
					Card card = Database.getInstance().getCardById(requestId);					
					controller.removeCardFromSide(card, 1);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			} 
			
			else if (action.equals("removeCardFromReinforcement")){
				// Remove card from reinforcement deck
				try {
					Card card = Database.getInstance().getCardById(requestId);					
					controller.removeCardFromReinforcement(card, 1);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			} 
			
			else if (req.getParameter("searchButton") != null) {
				ArrayList<Card> results = null;
				results = controller.searchForMatches(req);
				if (results != null){
					// remove cards that are not legal from search results
					// TODO: change to allow unlimited format
					Iterator<Card> iterator = results.iterator();
					while (iterator.hasNext()){
						Card card = iterator.next();
						if (card.getMaxBlockCopies() == 0){
							iterator.remove();
						}
					}
					
					// prepare the results to be displayed in rows of 4
					ArrayList<ArrayList<Card>> searchResults = new ArrayList<ArrayList<Card>>();
					ArrayList<Card> temp = new ArrayList<Card>();
					int j = 0;
					for (int i = 0; i < results.size(); i++){
						if (j == 4){
							searchResults.add(temp);
							temp = new ArrayList<Card>();
							j = 0;
						}
						temp.add(results.get(i));
						j++;
					}
					searchResults.add(temp);
								
					req.getSession().setAttribute("searchResults", searchResults);
				}
			}
			
			else if (req.getParameter("sortButton") != null) {
				controller.sortDeck();
			}
						
			// Check the deck for errors
			ArrayList<String> errors = controller.getDeckErrors();
			
			// Sort the deck
			controller.sortDeck();
			
			// prepare main deck to be displayed in rows of 10
			Deck tempDeck = controller.getDeck();
			if (!tempDeck.isMainDeckEmpty()){
				ArrayList<ArrayList<Card>> mainDeck = new ArrayList<ArrayList<Card>>();
				ArrayList<Card> temp = new ArrayList<Card>();
				int j = 0;
				for (int i = 0; i < tempDeck.getMainDeck().size(); i++){
					if (j == 10){
						mainDeck.add(temp);
						temp = new ArrayList<Card>();
						j = 0;
					}
					temp.add(deck.getMainDeck().get(i));
					j++;
				}
				mainDeck.add(temp);
				
				req.getSession().setAttribute("mainDeck", mainDeck);
			} else {
				req.getSession().setAttribute("mainDeck",  null);
			}
			
			// set other attributes
			req.getSession().setAttribute("deck", deck);
			req.setAttribute("errors", errors);
			ArrayList<Element> elementChoices = new ArrayList<Element>(Arrays.asList(Element.values()));
			req.setAttribute("elementChoices", elementChoices);
			ArrayList<Rarity> rarityChoices = new ArrayList<Rarity>(Arrays.asList(Rarity.values()));
			req.setAttribute("rarityChoices", rarityChoices);
			
			req.getRequestDispatcher("/view/deckBuilder.jsp").forward(req, resp);
			return;
		} else {
			throw new ServletException("Invalid post request.");
		}
	}
}
