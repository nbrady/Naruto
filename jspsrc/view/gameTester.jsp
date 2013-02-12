<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

	<head>
		<title>Game Tester</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		
		<script type="text/javascript">
       		$(document).ready(function() {
       			$("#playCardForm").validate();
       		});
       	</script>
	<head>
	
	<body>
		<div id="player1" style="float:left; width: 49%;">
			Hand: <br />
			<c:forEach var="card" items="${player1.hand}">
				${card.name} <br />
			</c:forEach>
			
			<p />
			
			Deck: <br />
			Number of Cards: ${numCardsInDeck1}
			
			<p />
			
			Chakra <br />
			<c:forEach var="card" items="${player1.chakra}">
				${card.name} <br />
			</c:forEach>
			
			<p />
			
			Discard Pile: <br />
			<c:forEach var="card" items="${player1.discardPile}">
				${card.name} <br />
			</c:forEach>
		</div>
		
		<div id="player2" style="float:left; width: 49%;">
			Hand: <br />
			<c:forEach var="card" items="${player2.hand}">
				${card.name} <br />
			</c:forEach>
			
			<p />
			
			Deck: <br />
			Number of Cards: ${numCardsInDeck2}
			
			<p />
			
			Chakra <br />
			<c:forEach var="card" items="${player2.chakra}">
				${card.name} <br />
			</c:forEach>
		</div>
		<div style="clear: both"></div>
		<p />
		Card effect:
		<form id="playCardForm" action="${pageContext.servletContext.contextPath}/gameTester" method="post">
			<textarea name="effectBox" size="" value="" class="required"></textarea>
			<input name="playCard" type="submit" value="Play Card" />
		</form>
				
	</body>
	
</html>