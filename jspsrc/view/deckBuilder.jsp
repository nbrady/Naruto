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
			var requestId;
		
       		$(document).ready(function() {
       			$(".addCardToMain").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'addCardToMain');
					$("addCardForm").submit();
				});
				
				$(".addCardToSide").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'addCardToSide');
					$("addCardForm").submit();
				});
				
				$(".removeCardFromMain").click(function() {
					alert(requestId);
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					alert(requestId);
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'removeCardFromMain');
					$("removeCardForm").submit();
				});
				
				$(".removeCardFromSide").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'addCardToSide');
					$("removeCardForm").submit();
				});
				
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
				$("#addCardForm").validate();

       		});
       	</script>
          	
	</head>
	
	<body>
		<div style="width: 47%; float: left;">
			<form id="addCardForm" action="${pageContext.servletContext.contextPath}/deckBuilder" method="post">
				<input type="hidden" name="submitted" value="true" />
				<input id="requestIdElt" type="hidden" name="requestId" value="-1" />
				<input id="actionElt" type="hidden" name="action" value="" />
				
				<table>
					<tr>
						<td class="text">Card Name: </td>
						<td> <input type="text" id="cardNameElt" name="cardNameBox" size="30" value="${cardName}" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td class="text">Card Number: </td>
						<td><input type="text" name="cardNumberBox" size="30" value="${cardNumber}" /></td>
						<td></td>
					</tr>
							
					<tr>
						<td><input name="searchButton" type="submit" value="Search" /></td>
					</tr>
				</table>
				
				<c:if test="${empty searchResults}"> 
					<br />
					No search results found.
				</c:if>
				
				<c:if test="${! empty searchResults}"> 
					<table border=1 style="width: 100%; text-align: center;">
						<tr>
							<th>Card Name</th>
							<th>Card Number</th>
							<th>Image</th>
							<th>Action</th>
							<th>Quantity</th>
						</tr>
						
						<c:forEach var="card" items="${searchResults}">
							<tr>
								<td>${card.cardName}</td>
								<td>${card.cardNumber}</td>
								<td><img src="/static/card_images/${card.cardNumber}_t.jpg" /></td>
								<td><input id="addCardToMainDeckButton${card.id}" class="addCardToMain" type="submit" value="Add To Main Deck" /><br />
									<input id="addCardToSideDeckButton${card.id}" class="addCardToSide" type="submit" value="Add To Side Deck" /></td>
								<td><input type="text" name="quantityBox${card.id}" size="2" value="${quantity}"/></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</form>
		</div>
		
		<p />
		<div style="width: 47%; float: left; margin-left: 20px">
			<form id="removeCardForm" action="${pageContext.servletContext.contextPath}/deckBuilder" method="post">
				<input type="hidden" name="submitted" value="true" />
				<input id="requestIdElt" type="hidden" name="requestId" value="-1" />
				<input id="actionElt" type="hidden" name="action" value="" />
			
				<c:if test="${! empty errors}">
					<c:forEach var="error" items="${errors}">
						<span class="error">${error}<br /></span>
					</c:forEach>
				</c:if>
				
				<br />
				
				Main Deck:
				<table border=1 style="width: 100%; text-align: center;">
					<tr>
						<th>Card Name</th>
						<th>Card Number</th>
						<th>Image</th>
						<th>Action</th>
						<th>Quantity</th>
					</tr>
					<c:forEach var="mainCard" items="${deck.mainDeck}">
						<tr>
							<td>${mainCard.cardName}</td>
							<td>${mainCard.cardNumber}</td>
							<td><img src="/static/card_images/${mainCard.cardNumber}_t.jpg" /></td>
							<td><input name="removeCardFromMainButton${mainCard.id}" class="removeCardFromMain" type="submit" value="Remove Card" /></td>
							<td><input type="text" name="removeMainQuantityBox${card.id}" size="2" value="${quantity}"/></td>
						</tr>
					</c:forEach>
				</table>
				
				<br />
				
				Side Deck:
				<table border=1 style="width: 100%; text-align: center;">
					<tr>
						<th>Card Name</th>
						<th>Card Number</th>
						<th>Image</th>
						<th>Attribute</th>
						<th>Action</th>
					</tr>
					<c:forEach var="sideCard" items="${deck.sideDeck}">
						<tr>
							<td>${sideCard.cardName}</td>
							<td>${sideCard.cardNumber}</td>
							<td><img src="/static/card_images/${sideCard.cardNumber}_t.jpg" /></td>
							<td><input name="removeCardFromSideButton${sideCard.id}" class="removeCardFromSide" type="submit" value="Remove Card" /></td>
							<td><input type="text" name="removeSideQuantityBox${card.id}" size="2" value="${quantity}"/></td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div>
	</body>
	
</html>