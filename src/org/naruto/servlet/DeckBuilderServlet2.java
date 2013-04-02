package org.naruto.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naruto.controller.DeckBuilderController;
import org.naruto.model.Card;
import org.naruto.model.Deck;
import org.naruto.model.persist.Database;

public class DeckBuilderServlet2 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/view/deckBuilder2.jsp").forward(req, resp);
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
				// Add card to maindeck
				try {
					Card card = Database.getInstance().getCardById(requestId);
					controller.addCardToMain(card, quantity);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
			
			else if (action.equals("addCardToSide")){
				// Add card to sidedeck
				try {
					Card card = Database.getInstance().getCardById(requestId);
					controller.addCardToSide(card, quantity);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			} 
			
			else if (action.equals("removeCardFromMain")){
				// Remove card from maindeck
				try {
					Card card = Database.getInstance().getCardById(requestId);					
					controller.removeCardFromMain(card, 1);
				} catch (SQLException e){
					e.printStackTrace();
				}
			} 
			
			else if (action.equals("removeCardFromSide")){
				// Remove card from sidedeck
				try {
					Card card = Database.getInstance().getCardById(requestId);					
					controller.removeCardFromSide(card, 1);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			} 
			
			else if (req.getParameter("searchButton") != null) {
				ArrayList<Card> results = controller.searchForMatches(req);
				if (results != null){
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
			
			// set deck
			Deck tempDeck = controller.getDeck();
			
			// Main deck
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
			
			// set other attributes
			req.getSession().setAttribute("deck", deck);
			req.setAttribute("errors", errors);
			
			req.getRequestDispatcher("/view/deckBuilder2.jsp").forward(req, resp);
		} else {
			throw new ServletException("Invalid post request.");
		}
	}
}
