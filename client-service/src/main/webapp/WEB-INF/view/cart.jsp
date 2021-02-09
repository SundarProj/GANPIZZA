<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>cart summary</title>
</head>

<body>
	<h3>Cart Summary</h3>

	<form:form modelAttribute="order" method="POST" action="/submitOrder" id="pizzaForm">
		<table>
			<c:forEach varStatus="p" var="pizza" items="${order.ganPizzaList}">
				<tr>
					<td><li><form:label
								path="ganPizzaList[${p.index}].number" />${pizza.number}</li></td>
					<td><form:label path="ganPizzaList[${p.index}].crust" />${pizza.crust}</td>
					<td><form:label path="ganPizzaList[${p.index}].sauce" />${pizza.sauce}</td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="/buildpizza"><input id="addButton" type="button" value='Build another pizza'></a></td>
				<td></td>
				<td><input id="pizzaSubmit" type="submit" value="Submit"></td>
			</tr>
		</table>
	</form:form>

	<script>
		$(document).ready(function() {
		  $('pizzaForm').on('submit', function(e){
		  	 alert("ThankYou");
			 $('pizzaSubmit').disabled = true;
		     $('addButton').disabled = true;
		  });
		});
	</script>
</body>

</html>