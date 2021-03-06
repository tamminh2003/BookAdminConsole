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
	<div align="center">
		<form action="AdminServlet" method="post">
			<button type="submit">Show All Books</button>
		</form>
	</div>
	<!-- ------------- -->

	<div align="center">
		<form action="${pageContext.request.contextPath}/AdminServlet" method="post">
		
		<c:if test="${book != null}">
			<input type="hidden" name="action" value="update">
		</c:if>
		
		<c:if test="${book == null}">
			<input type="hidden" name="action" value="insert">
		</c:if>
		
		<table border="1" cellpadding="5">
			
			<caption>
				<h1>
					<c:if test="${book != null}">
 						Edit Book
 					</c:if>
					<c:if test="${book == null}">
 						Add New Book
 					</c:if>
				</h1>
			</caption>
			
			<c:if test="${book != null}">
				<input type="hidden" name="bid"
					value="<c:out value='${book.getBid()}'/>" />
			</c:if>
			
			<tr>
				<th>Book Category:</th>
				<c:if test="${book != null}">
					<td><select name="cid">
					
					<c:forEach var="category" items="${categories}">
						<option
							<c:if test="${book.getCid() == category.getCid()}"> selected </c:if> 
							value="${category.getCid()}">
								<c:out value="${category.getCategoryTitle()}"/>
						</option>
					</c:forEach>
						
					</select></td>
				</c:if>
			
				<c:if test="${book == null}">
					<td><select name="cid">
						<c:forEach var="category" items="${categories}">
							<option value="${category.getCid()}">
									<c:out value="${category.getCategoryTitle()}"/>
							</option>
						</c:forEach>

					</select></td>
				</c:if>
				
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
					value="<c:out value='${book.getPublisheddate().toLocalDateTime().toLocalDate()}' />" /></td>
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