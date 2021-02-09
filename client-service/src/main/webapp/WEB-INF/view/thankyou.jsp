<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Thankyou Page</title>
</head>

<body>
	<h3>Thankyou for your order</h3>
	<h4>Note: Due to insufficient topping's quantities, some of the
		pizzas in your order might get cancelled. Sorry for the inconvenience.</h4>
	<table border="1">
		<THEAD>
			<TR>
				<TH>Pizza Number</TH>
				<TH>Crust Type</TH>
				<TH>Sauce Type</TH>
				<TH>Status</TH>
				<TH>Message</TH>
			</TR>
		</THEAD>
		<c:forEach varStatus="b" var="bakedServerResponse"
			items="${bakedServerResponseList}">
			<tr>
				<td>${bakedServerResponse.pizza.number}</td>
				<td>${bakedServerResponse.pizza.crust}</td>
				<td>${bakedServerResponse.pizza.sauce}</td>
				<td>${bakedServerResponse.pizza.status.value}</td>
				<td>${bakedServerResponse.pizza.message}</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</c:forEach>
		<tr>
			<td><a href="/returnHome"><input type=button
					value='Make another order'></a></td>

		</tr>
	</table>
</body>
</body>
</html>