<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<body>

		<form action="AdminServlet" method="GET">
			<input type="text" name="search" placeholder="Search by Category">
			<input type="hidden" name="action" value="search">
			<button type="submit">Search</button>
		</form>

</body>

</html>