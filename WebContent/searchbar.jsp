<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<body>

		<form action="AdminServlet" method="GET">
			<input type="text" name="search" value="Category">
			<input type="hidden" name="action" value="search">
			<input type="hidden" name="login" value="1">
			<button type="submit">Search</button>
		</form>

</body>

</html>