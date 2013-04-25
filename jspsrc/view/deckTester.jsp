<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

	<head>
		<title>Deck Tester</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		
		<script type="text/javascript">
       		$(document).ready(function() {
       			$("#drawCardForm").validate();
       		});
       	</script>
	<head>
	
	<body>
		<table>
			<tr>
				<td><a href="/deckBuilder">Build Deck</a></td>
				<td><a href="/deckTester">Test Deck</a></td>
				<td><a href="/deckPrinter" target="_blank">Print Deck</a></td>
			</tr>
		</table>
		<br />
		<div id="player1" style="float:left; width: 100%;">
			Hand: <br />
			<c:forEach var="card" items="${hand.mainDeck}">
				<img src="/static/card_images/${card.cardNumber}_t.jpg" />
			</c:forEach>
			
			<p />
			
			Number of Cards in Deck: ${numCardsInDeck1}
		</div>
		
		<div style="clear: both"></div>
		<form id="drawCardForm" action="${pageContext.servletContext.contextPath}/deckTester" method="post">
			<input name="drawCard" type="submit" value="Draw Card" />
			<input name="shuffleDeck" type="submit" value="Shuffle Deck" />
		</form>
				
	</body>
	
</html>