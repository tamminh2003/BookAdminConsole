<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Book Management Application</title>
<style>
tr, th, td {
	border: thin solid black;
	padding: 10px;
}

#search-container {
	width: fit-content;
	margin: auto;
	padding: 100px;
}
</style>

<script>
		function areYouSure(){
			confirm("Do you want to delete?");
		}
	</script>
</head>
<body style="font-family: arial, serif;">

	<div align="center">
	
		<h1>Admin Console</h1>
		
		<c:if test="${search != null}">
			<h1> <c:out value="${search} Search Result" /> </h1>
		</c:if>
		
		<c:if test="${search == null}">
			<h1>List of Books</h1>
		</c:if>
		
		<div id="nav-menu">
		
				<form action="AdminServlet" method="post">
					<button type="submit">Show All Books</button>
				</form>
				
				<form action="AdminServlet" method="post">
					<input type="hidden" name="action" value="new" />
					<button type="submit">Add New Book</button>
				</form>
				
				<form action="AdminServlet" method="post">
					<input type="hidden" name="action" value="adminLogout" />
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

					<td>|<a
						href="${pageContext.request.contextPath}/AdminServlet?action=edit&id=<c:out
						value='${book.getBid()}' />&login=1">Edit</a>|
						|<a
						onclick="return confirm('Are you sure you want to delete this entry?');"
						href="${pageContext.request.contextPath}/AdminServlet?action=delete&id=<c:out
						value='${book.getBid()}' />&login=1">Delete</a>|
						|<a
						href="${pageContext.request.contextPath}/AdminServlet?action=select&id=<c:out
						value='${book.getBid()}' />&login=1">Select</a>|
					</td>
				</tr>
			</c:forEach>
			
		</table>
		
	</div>

	<div id="search-container" align = center>
		<jsp:include page="searchbar.jsp" />
	</div>

</body>