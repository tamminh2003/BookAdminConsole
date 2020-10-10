<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Homepage</title>
</head>
<body>

	<div align="center">

		<h1>User Homepage</h1>

		<c:if test="${search != null}">
			<h1><c:out value="${search} Search Result" /></h1>
		</c:if>

		<c:if test="${search == null}">
			<h1>List of Books</h1>
		</c:if>

		<div id="nav-menu">
			<form action="UserServlet" method="post">
				<input type="hidden" name="action" value="userLogout" />
				<button type="submit">Logout</button>
			</form>
		</div>

		<table>

			<tr align=center>
				<th>BID</th>
				<th>Category</th>
				<th>Title</th>
				<th>Description</th>
				<th>Author</th>
				<th>Published Date</th>
				<th>ISBN</th>
				<th>Price</th>
				<th>Pages</th>
				<th></th>
			</tr>

			<c:forEach var="book" items="${listBook}">
				<tr align=center>

					<td><c:out value="${book.getBid()}" /></td>
					<td><c:out value="${book.getCategory()}" /></td>
					<td><c:out value="${book.getBooktitle()}" /></td>
					<td><c:out value="${book.getDescription()}" /></td>
					<td><c:out value="${book.getAuthor()}" /></td>
					<td><c:out value="${book.getPublisheddate()}" /></td>
					<td><c:out value="${book.getIsbn()}" /></td>
					<td><c:out value="${book.getPrice()}" /></td>
					<td><c:out value="${book.getNoofpages()}" /></td>

					<td><a
						href="${pageContext.request.contextPath}/UserServlet?action=select&id=<c:out
						value='${book.getBid()}' />&login=1">Select</a>|
					</td>
				</tr>
			</c:forEach>

		</table>

	</div>

	<div id="search-container" align=center>
		<form action="UserServlet" method="GET">
			<input type="text" name="search" placeholder="Search by Category">
			<input type="hidden" name="action" value="search">
			<button type="submit">Search</button>
		</form>
	</div>
</body>
</html>