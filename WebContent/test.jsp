<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test page</title>
</head>
<body>
	<c:out value="${test}" />
	<form action="UserServlet" method="post">
		<input type="hidden" name="action" value="userLogout">
		<button type="submit">Logout</button>
	</form>
</body>
</html>