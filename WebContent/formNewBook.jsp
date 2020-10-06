<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Books</title>
</head>
<body>
	<!-- List All Book -->
	<center>
		<h4>
			<a href="${pageContext.request.contextPath}/AdminServlet?action=list&login=1">List
				All Books</a>
		</h4>
	</center>
	<!-- ------------- -->

	<div align="center">
	
		<c:if test="${book != null}">
			<form action="${pageContext.request.contextPath}/AdminServlet" method="post">
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="login" value="1">
		</c:if>
		
		<c:if test="${book == null}">
			<form action="${pageContext.request.contextPath}/AdminServlet?login=1" method="post">
				<input type="hidden" name="action" value="insert">
				<input type="hidden" name="login" value="1">
		</c:if>
		
		<table border="1" cellpadding="5">
			
			<caption>
				<h2>
					<c:if test="${book != null}">
 						Edit Book
 					</c:if>
					<c:if test="${book == null}">
 						Add New Book
 					</c:if>
				</h2>
			</caption>
			
			<c:if test="${book != null}">
				<input type="hidden" name="bid"
					value="<c:out value='${book.getBid()}' />" />
			</c:if>
			
			<tr>
				<th>Book CID:</th>
				<td><select name="cid">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				</select></td>
				<!-- <input type="number" name="cid" required value="<c:out value='${book.getCid()}' />" /> -->
			</tr>
			
			<tr>
				<th>Book Title:</th>
				<td><input type="text" name="booktitle" size="45" required
					value="<c:out value='${book.getBooktitle()}' />" /></td>
			</tr>
			
			<tr>
				<th>Author:</th>
				<td><input type="text" name="author" size="45" required
					value="<c:out value='${book.getAuthor()}' />" /></td>
			</tr>
			
			<tr>
				<th>ISBN:</th>
				<td><input type="text" name="isbn" size="45" required
					value="<c:out value='${book.getIsbn()}' />" /></td>
			</tr>
			
			<tr>
				<th>Description:</th>
				<td><input type="text" name="description" size="45" required
					value="<c:out value='${book.getDescription()}' />" /></td>
			</tr>
			
			<tr>
				<th>Published Date:</th>
				<td><input type="date" name="publisheddate" size="45" required
					value="<c:out value='${book.getPublisheddate()}' />" /></td>
			</tr>
			
			<tr>
				<th>Price:</th>
				<td><input type="number" name="price" placeholder="1.0" step="0.01" required
					value="<c:out value='${book.getPrice()}' />" /></td>
			</tr>
			
			<tr>
				<th>Number of pages:</th>
				<td><input type="number" name="noofpages" placeholder="1" required
					value="<c:out value='${book.getNoofpages()}' />" /></td>
			</tr>
			
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save" /></td>
			</tr>
			
		</table>
		
		</form>
	</div>

</body>
</html>