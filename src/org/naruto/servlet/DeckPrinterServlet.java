package org.naruto.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naruto.model.Card;
import org.naruto.model.Deck;

public class DeckPrinterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Deck deck = (Deck) req.getSession().getAttribute("deck");
		if (deck == null){
			deck = new Deck();
		}
		
		// Separate by card type
		HashMap<Card, Integer> ninjas = new HashMap<Card, Integer>();
		HashMap<Card, Integer> missions = new HashMap<Card, Integer>();
		HashMap<Card, Integer> jutsus = new HashMap<Card, Integer>();
		HashMap<Card, Integer> clients = new HashMap<Card, Integer>();
		for(Card card : deck.getMainDeck()){
			if (card.isNinja() && !ninjas.containsKey(card)){
				ninjas.put(card, deck.getNumberInMainDeck(card));
			} else if (card.isMission() && !missions.containsKey(card)){
				missions.put(card, deck.getNumberInMainDeck(card));
			} else if (card.isJutsu() && !jutsus.containsKey(card)){
				jutsus.put(card, deck.getNumberInMainDeck(card));
			} else if (card.isClient() && !clients.containsKey(card)){
				clients.put(card, deck.getNumberInMainDeck(card));
			}
		}
		
		req.setAttribute("ninjas", ninjas);
		req.setAttribute("missions", missions);
		req.setAttribute("jutsus", jutsus);
		req.setAttribute("clients", clients);
		
		req.getRequestDispatcher("/view/deckPrinter.jsp").forward(req, resp);
	}

}
