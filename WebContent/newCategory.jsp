<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Books</title>
</head>
<body>

	<div align="center">

		<form action="AdminServlet" method="post">
			<input type="hidden" name="action" value="category" /> 
			<input type="submit" value="Back" />
		</form>

		<form action="${pageContext.request.contextPath}/AdminServlet" method="post">
			<input type="hidden" name="action" value="editCategory">
			<input type="hidden" name="cid" value="<c:out value='${category.getCid()}' />" />
			<table border="1" cellpadding="5">
				<caption>
					<h2>Edit Category</h2>
				</caption>
				<tr>
					<th>Category Title:</th>
					<td><input type="text" name="categorytitle" size="45" required
						value="<c:out value='${category.getCategoryTitle()}' />" /></td>
				</tr>
			</table>
			<br>
			<input type="submit" value="Edit" />
		</form>
		
	</div>

</body>
</html>