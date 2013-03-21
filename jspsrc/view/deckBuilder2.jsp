<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Deck Builder</title>		
					
		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>	
		<script type="text/javascript" src="/static/js/jquery-ui-1.8.21.custom.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/static/css/jquery-ui-1.8.21.custom.css" />
		
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
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'removeCardFromMain');
					$("addCardForm").submit();
				});
				
				$(".removeCardFromSide").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'removeCardFromSide');
					$("addCardForm").submit();
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
							
       		});
       	</script>  	
       	
       	<script type="text/javascript">
       	  	function addCard(str){
       	  		alert("gets here");
         		requestId = str;
         		alert(requestId);
				$("#requestIdElt").attr('value', requestId);
				$("#actionElt").attr('value', 'addCardToMain');
				$("addCardForm").submit();
          	}
        </script>
	</head>
	
	<body>
		<table>
			<tr>
				<td><a href="/deckBuilder">Build Deck</a></td>
				<td><a href="/deckTester">Test Deck</a></td>
				<td><a href="/deckPrinter" target="_blank">Print Deck</a></td>
			</tr>
		</table>
		<br />
		<form id="addCardForm" action="${pageContext.servletContext.contextPath}/deckBuilder2" method="post">
			<div style="width: 25%; height: 90%; float: left;  border: 1px solid black; overflow-y: scroll;">
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
					<table border=1 style="width: 100%; text-align: center; border-collapse: collapse;">						
						<c:forEach var="result" items="${searchResults}">
							<tr>
								<c:forEach var="card" items="${result}">
									<td><input type="image" id="addCardToMainButton${card.id}" class="addCardToMain" src="/static/card_images/${card.cardNumber}_t.jpg" /></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		
			<p />
			<div style="width: 47%; float: left; margin-left: 20px">			
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
					</tr>
					<c:forEach var="mainCard" items="${deck.mainDeck}">
						<tr>
							<td>${mainCard.cardName}</td>
							<td>${mainCard.cardNumber}</td>
							<td><img src="/static/card_images/${mainCard.cardNumber}_t.jpg" /></td>
							<td><input id="removeCardFromMainButton${mainCard.id}" class="removeCardFromMain" type="submit" value="Remove Card" /></td>
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
						<th>Action</th>
					</tr>
					<c:forEach var="sideCard" items="${deck.sideDeck}">
						<tr>
							<td>${sideCard.cardName}</td>
							<td>${sideCard.cardNumber}</td>
							<td><img src="/static/card_images/${sideCard.cardNumber}_t.jpg" /></td>
							<td><input id="removeCardFromSideButton${sideCard.id}" class="removeCardFromSide" type="submit" value="Remove Card" /></td>d>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</body>	
</html>