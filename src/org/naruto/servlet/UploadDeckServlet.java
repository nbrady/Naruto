package org.naruto.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.naruto.model.Card;
import org.naruto.model.Deck;
import org.naruto.model.persist.Database;

public class UploadDeckServlet extends HttpServlet{
	public static final int MAX_MEMORY_SIZE = 200000; 
	public static final int MAX_FILE_SIZE = 4000000; 
	public static final int MAX_REQUEST_SIZE = MAX_FILE_SIZE + 10000;
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if (ServletFileUpload.isMultipartContent(req)) {
			Deck deck = new Deck();
			
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			factory.setSizeThreshold(MAX_MEMORY_SIZE);
			factory.setRepository(new File("/tmp"));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(MAX_REQUEST_SIZE);

			// Parse the request
			try {
				List<?> items = upload.parseRequest(req);
				
				FileItem item = findDeck(items);
				if (item == null) {
					throw new FileUploadException("No deck file item found");
				}
				
				String filename = FilenameUtils.getName(item.getName());
				File file = new File(filename);
				
				item.write(file);		
				
				BufferedReader reader = null;
				try {
					
					String currentLine;
					reader = new BufferedReader(new FileReader(file));
		 
					while ((currentLine = reader.readLine()) != null) {
						// Create card and add it to deck
						System.out.println(currentLine.substring(currentLine.lastIndexOf("\t") + 1));
						int quantity = Integer.parseInt(currentLine.substring(0, 1));
						String cardNumber = currentLine.substring(currentLine.lastIndexOf("\t") + 1);
						Card card = Database.getInstance().getCardByCardNumber(cardNumber);
						for (int i = 0; i < quantity; i++){
							System.out.println("Adding " + card.getCardName());
							deck.getMainDeck().add(card);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Gets here");
			
			req.getSession().setAttribute("deck", deck);
			
			//req.getRequestDispatcher("/view/deckBuilder2.jsp").forward(req, resp);
			resp.sendRedirect("/deckBuilder");
		}
	}
	
	private FileItem findDeck(List<?> items) {
		for (Object o : items) {
			FileItem fileItem = (FileItem) o;
			if (!fileItem.isFormField()) {
				// found it
				return fileItem;
			}
		}
		return null;
	}

}
