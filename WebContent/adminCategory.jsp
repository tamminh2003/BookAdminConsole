<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
tr, th, td, table {
	border: thin solid black;
	padding: 30px;
}
</style>
<meta charset="ISO-8859-1">
<title>Manage Category</title>
</head>
<body>

	<div align="center">
	
		<h1>Category Managing Menu</h1>
		<a href="AdminServlet">Back</a>
		
		<br><br>
		
		<div id="nav-menu">
			<form action="AdminServlet" method="post">
				<input type="hidden" name="action" value="addCategory" />
				<button type="submit">Add New Category</button>
			</form>
		</div>
		
		<br><br>
		
		<table>
			<tr align=center>
				<th>CID</th>
				<th>Category Title</th>

				<th></th>

			</tr>

			<c:forEach var="category" items="${listCategory}">
				<tr align=center>

					<td><c:out value="${category.getCid()}" /></td>
					<td><c:out value="${category.getCategoryTitle()}" /></td>

					<td>|<a
						href="${pageContext.request.contextPath}/AdminServlet?action=showEditCategory&id=<c:out
						value='${category.getCid()}' />&login=1">Edit</a>|
						|<a
						onclick="return confirm('Are you sure you want to delete this entry?');"
						href="${pageContext.request.contextPath}/AdminServlet?action=deleteCategory&id=<c:out
						value='${category.getCid()}' />&login=1">Delete</a>|
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</body>
</html>