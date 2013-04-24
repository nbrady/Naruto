package org.naruto.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.naruto.model.Card;
import org.naruto.model.Deck;

public class SaveDeckServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{  
	    // Form submission
		if (req.getParameter("saveDeckButton") != null) { 
			ServletOutputStream outputStream = null;
			try {
				outputStream = resp.getOutputStream( );
			
			     // set response headers
			     resp.setContentType("text/plain"); 
			     resp.addHeader("Content-Disposition","attachment; filename=deck.txt");
			     
			     Deck deck = (Deck) req.getSession().getAttribute("deck");
			     
			     // create hashmap that contains the quantities
			     HashMap<Card, Integer> map = new HashMap<Card, Integer>();
			     for (Card card : deck.getMainDeck()){
			    	 map.put(card, deck.getNumberInMainDeck(card));
			     }
			     	     
			    for (Card card : map.keySet()){
			    	outputStream.println(deck.getNumberInMainDeck(card) + "\t" + StringUtils.rightPad(card.getCardName(), 50) + "\t" + card.getCardNumber());
			    }
			} finally {
				if (outputStream != null){
					outputStream.close();
				}
			}
			
			resp.sendRedirect("/deckBuilder");
			return;
		}
	}
}
