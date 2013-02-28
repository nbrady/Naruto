<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Deck Builder</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		
		<link rel="stylesheet" type="text/css" href="/static/css/jquery-ui-1.8.21.custom.css" />
		<script type="text/javascript" src="/static/js/jquery-ui-1.8.21.custom.min.js"></script>
		
		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		
		<script type="text/javascript">
       		$(document).ready(function() {
       			$("#cardNameElt").autocomplete({
					source: function(req, resp) {
						$.ajax({
							type: 'GET',
							url: "/cardNameCompletion?term=" + req.term,
							success: function(data, textStatus, jqXHR) {
								resp(data);
	                    	}
						});
					}
				});
       		});
       	</script>
          	
	</head>
	
	<body>
		<div style="height: 50%; overflow-y: scroll;">
			<form id="addCardForm" action="${pageContext.servletContext.contextPath}/deckBuilder" method="post">
				<input type="hidden" name="submitted" value="true" />
				<table>
					<tr>
						<td class="text">Card Name: </td>
						<td> <input type="text" id="cardNameElt" name="cardNameBox" size="12" value="${cardName}" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td class="text">Card Number: </td>
						<td><input type="text" name="cardNumberBox" size="12" value="${cardNumber}" /></td>
						<td></td>
					</tr>
							
					<tr>
						<td class="text">Quantity: </td>
						<td><input type="text" name="quantityBox" size="12" value="${quantity}"/></td>
						<td></td>
					</tr>
					<tr>
						<td><input name="searchButton" type="submit" value="Search" /></td>
						<td><input name="addCardToMainButton" type="submit" value="Add To Main Deck" /></td>
						<td><input name="addCardToSideButton" type="submit" value="Add To Side Deck" /></td>
					</tr>
				</table>
				
				<c:if test="${! empty searchResults}"> 
					<table border=1 style="width: 100%;">
						<tr>
							<th>Card Name</th>
							<th>Card Number</th>
							<th>Elements</th>
							<th>Turn/Chakra Cost</th>
							<th>Hand Cost</th>
							<th>Healthy Stats</th>
							<th>Injured Stats</th>
							<th>Attribute</th>
							<th></th>
							<th></th>
						</tr>
						
						<c:forEach var="card" items="${searchResults}">
							<tr>
								<td>${card.cardName}</td>
								<td>${card.cardNumber}</td>
								<td>${card.elements}</td>
								<td>${card.turnChakraCost}</td>
								<td>${card.handCost}</td>
								<td>${card.healthyStats}</td>
								<td>${card.injuredStats}</td>
								<td>${card.attribute}</td>
								<td><input name="addCardToMainButton" type="submit" value="Add To Main Deck" /></td>
								<td><input name="addCardToSideButton" type="submit" value="Add To Side Deck" /></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</form>
		</div>
		
		<p />
		<div style="height: 50%; overflow-y: scroll;">
			<c:if test="${! empty errors}">
				<c:forEach var="error" items="${errors}">
					<span class="error">${error}<br /></span>
				</c:forEach>
			</c:if>
			
			<br />
			
			Main Deck:
			<table border=1 style="width: 100%;">
				<tr>
					<th>Card Name</th>
					<th>Card Number</th>
					<th>Elements</th>
					<th>Turn/Chakra Cost</th>
					<th>Hand Cost</th>
					<th>Healthy Stats</th>
					<th>Injured Stats</th>
					<th>Attribute</th>
					<th</th>
				</tr>
				<c:forEach var="mainCard" items="${deck.mainDeck}">
					<tr>
						<td>${mainCard.cardName}</td>
						<td>${mainCard.cardNumber}</td>
						<td>${mainCard.elements}</td>
						<td>${mainCard.turnChakraCost}</td>
						<td>${mainCard.handCost}</td>
						<td>${mainCard.healthyStats}</td>
						<td>${mainCard.injuredStats}</td>
						<td>${mainCard.attribute}</td>
						<td><input name="removeCardFromMainButton" type="submit" value="Remove Card" />
					</tr>
				</c:forEach>
			</table>
			
			<br />
			
			Side Deck:
			<table border=1 style="width: 100%;">
				<tr>
					<th>Card Name</th>
					<th>Card Number</th>
					<th>Elements</th>
					<th>Turn/Chakra Cost</th>
					<th>Hand Cost</th>
					<th>Healthy Stats</th>
					<th>Injured Stats</th>
					<th>Attribute</th>
					<th></th>
				</tr>
				<c:forEach var="sideCard" items="${deck.sideDeck}">
					<tr>
						<td>${sideCard.cardName}</td>
						<td>${sideCard.cardNumber}</td>
						<td>${sideCard.elements}</td>
						<td>${sideCard.turnChakraCost}</td>
						<td>${sideCard.handCost}</td>
						<td>${sideCard.healthyStats}</td>
						<td>${sideCard.injuredStats}</td>
						<td>${sideCard.attribute}</td>
						<td><input name="removeCardFromSideButton" type="submit" value="Remove Card" />
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
	
</html>