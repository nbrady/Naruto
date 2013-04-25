package org.naruto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naruto.model.Card;
import org.naruto.model.Deck;

public class DeckTesterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{	
		Deck deck = (Deck) req.getSession().getAttribute("deck");		
		req.setAttribute("numCardsInDeck1", deck.getMainDeck().size());
		
		req.getRequestDispatcher("/view/deckTester.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Deck deck = (Deck) req.getSession().getAttribute("deck");
		Deck hand = (Deck) req.getSession().getAttribute("hand");
		
		if (hand == null){
			hand = new Deck();
		}
		// Form submission
		if (req.getParameter("drawCard") != null){		
			if (!deck.isMainDeckEmpty()){
				Card card = deck.getCardFromMainDeck(0);
				deck.removeCardFromMainDeck(card);
				hand.addCardToMainDeck(card);
			}
		} else if (req.getParameter("shuffleDeck") != null){
			deck.shuffleMainDeck();
		}
		
		req.getSession().setAttribute("deck", deck);
		req.getSession().setAttribute("hand", hand);
		req.setAttribute("numCardsInDeck1", deck.getMainDeck().size());
		
		req.getRequestDispatcher("/view/deckTester.jsp").forward(req, resp);
	}
}
