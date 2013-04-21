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
		<table style="width: 100%">
			<tr>
				<td><a href="/deckBuilder">Build Deck</a></td>
				<td><a href="/deckTester">Test Deck</a></td>
				<td><a href="/deckPrinter" target="_blank">Print Deck</a></td>
			</tr>
		</table>
		<br />
		<form name="addCardForm" id="addCardForm" action="${pageContext.servletContext.contextPath}/deckBuilder" method="post">
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
  								<option value="NONE">Show All</option> 
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
  								<option value="NONE">Show All</option> 
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
  								<option value="-1">Show All</option> 
   								<option value="0">0</option>
   								<option value="1">1</option>
   								<option value="2">2</option>
					      	</select>
                    	</td>
                    
                    	<td></td>
                  	</tr>
                  	
                  	<tr>
						<td class="text">Card Type: </td>
						
						<td>
							<select name="cardTypeBox">
  								<option value="NONE">Show All</option> 
   								<option value="Ninja">Ninja</option>
   								<option value="Mission">Mission</option>
   								<option value="Jutsu">Jutsu</option>
   								<option value="Client">Client</option>
					      	</select>
                    	</td>
                    
                    	<td></td>
                  	</tr>
                  	
                 	<tr>
						<td class="text">Set: </td>
						
						<td>
							<select name="setBox">
  								<option value="NONE">Show All</option> 
   								<option value="28">28 - Ultimate Ninja Storm 3</option>
   								<option value="27">27 - Hero's Ascension</option>
   								<option value="26">26 - Avenger's Wrath</option>
   								<option value="25">25 - Kage Summit</option>
   								<option value="24">24 - Sage's Legacy</option>
   								<option value="23.5">23.5 - Tournament Pack 4</option>
   								<option value="23">23 - Invasion</option>
   								<option value="22">22 - Weapons of War</option>	
					      	</select>
                    	</td>
                    
                    	<td></td>
                  	</tr>
                  	
                  	<tr>
						<td class="text">Card Rarity: </td>
						
						<td>
							<select name="cardRarityBox">
  								<option value="NONE">Show All</option> 
   								<c:forEach var="rarity" items="${rarityChoices}">
   									<option value="${rarity.value}">${rarity.value}</option>
   								</c:forEach>
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
									<td><input oncontextmenu="return false" type="image" id="addCardButton${card.id}" class="addCard" src="/static/card_images/${card.cardNumber}_t.jpg" 
									title='Name: ${card.cardName} &#xA;Element: ${card.elements} &#xA;Turn/Chakra Cost: ${card.turnChakraCost} &#xA;Hand Cost: ${card.handCost} &#xA;Characteristics: ${card.characteristics} &#xA;Combat Attribute: ${card.attribute} &#xA;Stats: ${card.healthyStats} &nbsp;&nbsp;${card.injuredStats} &#xA;Effect: ${card.effect} &#xA;Rarity: ${card.rarity.value}'/></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		
			<p />
			<div style="width: 60%; float: left; margin-left: 20px">							

				Main Deck:
				
				<c:if test="${! empty mainDeck}">
					<table border=1 style="text-align: center; border-collapse: collapse;">
						<c:forEach var="row" items="${mainDeck}">	
							<tr>
								<c:forEach var="mainCard" items="${row}">
									<td>
										<input type="image" id="removeCardFromMainButton${mainCard.id}" class="removeCardFromMain" src="/static/card_images/${mainCard.cardNumber}_t.jpg" 
										title='Name: ${mainCard.cardName} &#xA;Element: ${mainCard.elements} &#xA;Turn/Chakra Cost: ${mainCard.turnChakraCost} &#xA;Hand Cost: ${mainCard.handCost} &#xA;Characteristics: ${mainCard.characteristics} &#xA;Combat Attribute: ${mainCard.attribute} &#xA;Stats: ${mainCard.healthyStats} &nbsp;&nbsp;${mainCard.injuredStats} &#xA;Effect: ${mainCard.effect} &#xA;Rarity: ${mainCard.rarity.value}'/>
									</td>
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
								<td><input type="image" id="removeCardFromSideButton${sideCard.id}" class="removeCardFromSide" src="/static/card_images/${sideCard.cardNumber}_t.jpg" 
								title='Name: ${sideCard.cardName} &#xA;Element: ${sideCard.elements} &#xA;Turn/Chakra Cost: ${sideCard.turnChakraCost} &#xA;Hand Cost: ${sideCard.handCost} &#xA;Characteristics: ${sideCard.characteristics} &#xA;Combat Attribute: ${sideCard.attribute} &#xA;Stats: ${sideCard.healthyStats} &nbsp;&nbsp;${sideCard.injuredStats} &#xA;Effect: ${sideCard.effect} &#xA;Rarity: ${sideCard.rarity.value}'/></td>
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
								<td><input type="image" id="removeCardFromReinforcementButton${reinforcementCard.id}" class="removeCardFromReinforcement" src="/static/card_images/${reinforcementCard.cardNumber}_t.jpg" 
								title='Name: ${reinforcementCard.cardName} &#xA;Element: ${reinforcementCard.elements} &#xA;Turn/Chakra Cost: ${reinforcementCard.turnChakraCost} &#xA;Hand Cost: ${reinforcementCard.handCost} &#xA;Characteristics: ${reinforcementCard.characteristics} &#xA;Combat Attribute: ${reinforcementCard.attribute} &#xA;Stats: ${reinforcementCard.healthyStats} &nbsp;&nbsp;${reinforcementCard.injuredStats} &#xA;Effect: ${reinforcementCard.effect} &#xA;Rarity: ${reinforcementCard.rarity.value}'/></td>
							</c:forEach>
						</tr>	
					</table>
				</c:if>
				
				<c:if test="${ empty deck.reinforcementDeck}">
					<p>No cards in reinforcement deck.</p>
				</c:if>
				
			</div>
		</form>
		
		<form action="${pageContext.servletContext.contextPath}/uploadDeck" method="post" enctype="multipart/form-data">
			<div style="width: 10%; float: left;">
				<div style="border: 1px solid black;">
					Upload Deck: <p />
					<input type="file" name="file" size="50" />
					<p />
					<input type="submit" value="Upload" />		
				</div>
				
				<p />
				
				<input type="submit" value="Save Deck" />
				
				<c:if test="${! empty errors}">
					<c:forEach var="error" items="${errors}">
						<span class="error">${error}<br /></span>
					</c:forEach>
				</c:if>
			</div>
		</form>
	</body>	
</html>
