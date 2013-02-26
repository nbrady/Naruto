package org.naruto.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naruto.model.persist.Database;

public class CardNameCompletion extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String term = req.getParameter("term");
		
		List<String> suggestions = new ArrayList<String>();
		
		// Require at least 3 letters before getting suggestions from database.
		if (term.length() >= 3) {			
			try {
				suggestions.addAll(Database.getInstance().suggestCardNames(term));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		// Encode the suggestions as a JSON array of strings.
		StringBuilder result = new StringBuilder();
		int count = 0;
		result.append("[");
		for (String suggestion : suggestions) {
			if (count > 0) {
				result.append(", ");
			}
			
			result.append("\"");
			result.append(suggestion);
			result.append("\"");
			
			count++;
		}
		result.append("]");
		
		resp.setContentType("application/json");
		resp.getWriter().println(result.toString());
	}
}
