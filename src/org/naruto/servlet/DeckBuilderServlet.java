package org.naruto.servlet;

import java.io.IOException;

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
	    // Form submission
		if (req.getParameter("submitted") != null) { 
			// Get current deck 
			Deck deck = (Deck) req.getSession().getAttribute("deck");
			if (deck == null){
				// No deck found, create a new empty deck
				deck = new Deck();
			}
			
			// Initialize controller
			DeckBuilderController controller = new DeckBuilderController();
			controller.setDeck(deck);
		
			// Parse fields
			String cardName = req.getParameter("cardNameBox");
			String cardNumber = req.getParameter("cardNumberBox");
			int quantity = Integer.parseInt(req.getParameter("quantityBox"));
			
			// Add card to maindeck
			if (req.getParameter("addCardToMainButton") != null){
				Card card = Database.getInstance().getCardByCardNumber(cardNumber);
				controller.addCardToMain(card, quantity);
			} else if (req.getParameter("addCardToSideButton") != null){
				Card card = Database.getInstance().getCardByCardNumber(cardNumber);
				controller.addCardToSide(card, quantity);
			}
			
			// Check the deck for errors
			ArrayList<String> errors = controller.getDeckErrors();
			
			// set request attributes
			req.getSession().setAttribute("deck", controller.getDeck());
			req.setAttribute("errors", errors);
			
			req.getRequestDispatcher("/view/createDeck.jsp").forward(req, resp);
		} else {
			throw new ServletException("Invalid post request.");
		}
	}
}
