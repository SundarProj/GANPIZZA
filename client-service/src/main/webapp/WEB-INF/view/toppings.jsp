<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>pick your toppings</title>
</head>

<body>
	<h3>Please pick your Toppings</h3>

	<form:form modelAttribute="pizza" method="POST" action="/addToCart" id="addToCartForm">
		<table>
			<tr>
				<td><form:label path="crust">Crust:</form:label></td>
				<td><form:radiobuttons items="${crusts}" path="crust" /></td>
			</tr>
			<tr>
				<td><form:label path="sauce">Sauce:</form:label></td>
				<td><form:radiobuttons items="${sauces}" path="sauce" /></td>
			</tr>
			<c:forEach varStatus="vs" var="topping" items="${pizza.toppings}">
				<tr>
					<td><form:input type="hidden" path="toppings[${vs.index}].ingredient"/>${topping.ingredient}</td>
					<td><form:input path="toppings[${vs.index}].quantity" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</body>
</html>