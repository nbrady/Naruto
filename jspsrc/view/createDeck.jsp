<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

	<head>
		<title>Create Deck</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		
		<script type="text/javascript">
       		$(document).ready(function() {
       			$("#addCardForm").validate();
       		});
       	</script>
	<head>
	
	<body>
		<form id="addCardForm" action="${pageContext.servletContext.contextPath}/createDeck" method="post">
			<table>
				<tr>
					<td class="text">Card Name: </td>
					<td> <input type="text" name="cardNameBox" size="12" value="${cardName}" class="required"/></td>
				</tr>
				
				<tr>
					<td class="text">Card Number: </td>
					<td><input type="text" name="cardNumberBox" size="12" value="${cardNumber}" class="required digits"/></td>
				</tr>
						
				<tr>
					<td class="text">Quantity: </td>
					<td><input type="text" name="quantityBox" size="12" value="${quantity}" class="required digits"/></td>
				</tr>
						
				<tr>
					<td> <input name="addCardToMainButton" type="submit" value="Add To Main Deck" /></td>
					<td> <input name="addCardToSideButton" type="submit" value="Add To Side Deck" /></td>
				</tr>
			</table>
		</form>
		
		<p />
		
		<c:if test="${! empty errors}">
			<c:forEach var="error" items="${errors}">
				<span class="error">${error}<br /></span>
			</c:forEach>
		</c:if>
		
		<br />
		
		Main Deck:
		<table border=1>
			<tr>
				<th>Card Name</th>
				<th>Card Number</th>
			</tr>
			<c:forEach var="mainCard" items="${deck.mainDeck}">
				<tr>
					<td>${mainCard.name}</td>
					<td>${mainCard.number}</td>
				</tr>
			</c:forEach>
		</table>
		
		<br />
		
		Side Deck:
		<table border=1>
			<tr>
				<th>Card Name</th>
				<th>Card Number</th>
			</tr>
			<c:forEach var="sideCard" items="${deck.sideDeck}">
				<tr>
					<td>${sideCard.name}</td>
					<td>${sideCard.number}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
	
</html>