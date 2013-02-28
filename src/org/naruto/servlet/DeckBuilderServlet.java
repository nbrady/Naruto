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

public class DeckBuilderServlet extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
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
			//String cardName = req.getParameter("cardNameBox");
			String cardNumber = req.getParameter("cardNumberBox");
			int quantity = 1;
			try {
				quantity = Integer.parseInt(req.getParameter("quantityBox"));
			} catch (NumberFormatException e){
				// No quantity is required to perform a search
			}
			if (req.getParameter("addCardToMainButton") != null){
				// Add card to maindeck
				Card card;
				try {
					card = Database.getInstance().getCardByCardNumber(cardNumber);
					controller.addCardToMain(card, quantity);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (req.getParameter("addCardToSideButton") != null){
				// Add card to sidedeck
				Card card;
				try {
					card = Database.getInstance().getCardByCardNumber(cardNumber);
					controller.addCardToSide(card, quantity);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			} else if (req.getParameter("searchButton") != null) {
				ArrayList<Card> searchResults = controller.searchForMatches(req);
				req.setAttribute("searchResults", searchResults);
			}
			
			// Check the deck for errors
			ArrayList<String> errors = controller.getDeckErrors();
			
			// set request attributes
			req.getSession().setAttribute("deck", controller.getDeck());
			req.setAttribute("errors", errors);
			
			req.getRequestDispatcher("/view/deckBuilder.jsp").forward(req, resp);
		} else {
			throw new ServletException("Invalid post request.");
		}
	}
}
