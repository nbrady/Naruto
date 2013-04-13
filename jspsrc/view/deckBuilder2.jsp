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
				$('.addCard').mousedown(function(event) {
					switch (event.which) {
				        case 1:
				        	requestId = $(this).attr('id').match(/(\d+)$/)[1];
							$("#requestIdElt").attr('value', requestId);
							$("#actionElt").attr('value', 'addCardToMain');
							document.addCardForm.submit();
				            break;
				        case 2:
				            requestId = $(this).attr('id').match(/(\d+)$/)[1];
							$("#requestIdElt").attr('value', requestId);
							$("#actionElt").attr('value', 'addCardToReinforcement');
							document.addCardForm.submit();
				            break;
				        case 3:				        	
				        	requestId = $(this).attr('id').match(/(\d+)$/)[1];
							$("#requestIdElt").attr('value', requestId);
							$("#actionElt").attr('value', 'addCardToSide');
							document.addCardForm.submit();
				            break;
				        default:
				            alert('You have a strange mouse');
				    }
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
				
				$(".removeCardFromReinforcement").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'removeCardFromReinforcement');
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
		<form name="addCardForm" id="addCardForm" action="${pageContext.servletContext.contextPath}/deckBuilder2" method="post">
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
						<td class="text">Combat Attribute: </td>
						<td><input type="text" name="combatAttributeBox" size="30" value="${combatAttribute}" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td class="text">Characteristic: </td>
						<td><input type="text" name="characteristicBox" size="30" value="${characteristic}" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td class="text">Effect: </td>
						<td><input type="text" name="effectBox" size="30" value="${effect}" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td class="text">Element: </td>
						
						<td>
							<select name="elementBox">
  								<option value="NONE">--- Select ---</option> 
   								<c:forEach var="element" items="${elementChoices}">
   									<option value="${element.value}">${element.value}</option>
   								</c:forEach>
					      	</select>
                    	</td>
                    	
                    	<td></td>
                  	</tr>
                  	
                  	<tr>
						<td class="text">Turn Cost: </td>
						
						<td>
							<select name="turnCostBox">
  								<option value="NONE">--- Select ---</option> 
   								<option value="0">0</option>
   								<option value="1">1</option>
   								<option value="2">2</option>
   								<option value="3">3</option>
   								<option value="4">4</option>
   								<option value="5">5</option>
   								<option value="6">6</option>
   								<option value="7">7</option>
   								<option value="8">8</option>
   								<option value="9">9</option>
					      	</select>
                    	</td>
                    
                    	<td></td>
                  	</tr>
                  	
                  	<tr>
						<td class="text">Hand Cost: </td>
						
						<td>
							<select name="handCostBox">
  								<option value="-1">--- Select ---</option> 
   								<option value="0">0</option>
   								<option value="1">1</option>
   								<option value="2">2</option>
					      	</select>
                    	</td>
                    
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
					<table border=1 style="text-align: center; border-collapse: collapse;">						
						<c:forEach var="result" items="${searchResults}">
							<tr>
								<c:forEach var="card" items="${result}">
									<td><input oncontextmenu="return false" style="width: 62px; height: 87px;" type="image" id="addCardButton${card.id}" class="addCard" src="/static/card_images/${card.cardNumber}_t.jpg" /></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		
			<p />
			<div style="width: 60%; float: left; margin-left: 20px">			
				<c:if test="${! empty errors}">
					<c:forEach var="error" items="${errors}">
						<span class="error">${error}<br /></span>
					</c:forEach>
				</c:if>
				
				<br />
				
				Main Deck:
				
				<c:if test="${! empty mainDeck}">
					<table border=1 style="text-align: center; border-collapse: collapse;">
						<c:forEach var="row" items="${mainDeck}">	
							<tr>
								<c:forEach var="mainCard" items="${row}">
									<td><input style="width: 62px; height: 87px;" type="image" id="removeCardFromMainButton${mainCard.id}" class="removeCardFromMain" src="/static/card_images/${mainCard.cardNumber}_t.jpg" /></td>
								</c:forEach>
							</tr>	
						</c:forEach>
					</table>
				</c:if>
				
				<c:if test="${ empty mainDeck}">
					<p>No cards in main deck.</p>
				</c:if>
				
				<br />
				
				Side Deck:
				<c:if test="${! empty deck.sideDeck}">
					<table border=1 style="text-align: center; border-collapse: collapse;">
						<tr>
							<c:forEach var="sideCard" items="${deck.sideDeck}">
								<td><input style="width: 62px; height: 87px;" type="image" id="removeCardFromSideButton${sideCard.id}" class="removeCardFromSide" src="/static/card_images/${sideCard.cardNumber}_t.jpg" /></td>
							</c:forEach>
						</tr>	
					</table>
				</c:if>
				
				<c:if test="${ empty deck.sideDeck}">
					<p>No cards in side deck.</p>
				</c:if>
				
				<br />
				
				Reinforcement Deck:
				<c:if test="${! empty deck.reinforcementDeck}">
					<table border=1 style="text-align: center; border-collapse: collapse;">
						<tr>
							<c:forEach var="reinforcementCard" items="${deck.reinforcementDeck}">
								<td><input style="width: 62px; height: 87px;" type="image" id="removeCardFromReinforcementButton${reinforcementCard.id}" class="removeCardFromReinforcement" src="/static/card_images/${reinforcementCard.cardNumber}_t.jpg" /></td>
							</c:forEach>
						</tr>	
					</table>
				</c:if>
				
				<c:if test="${ empty deck.reinforcementDeck}">
					<p>No cards in reinforcement deck.</p>
				</c:if>
				
				<br />
				<input name="sortButton" type="submit" value="Sort Deck" />
				
			</div>
		</form>		
	</body>	
</html>