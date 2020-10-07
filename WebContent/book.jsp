<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*" %>


<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">

<!-- TODO ADD book title here ${book.getBooktitle()} -->
<title><c:out value="${book.getBooktitle()}" /></title>

</head>

<body>
	<div id="book-container" align="center">
		<table>
			
			<tr>
				<td id="book-image"><!--  TODO ADD placeholder image --></td>
				<td id="book- details">
					<h1><c:out value="${book.getBooktitle()}" /></h1>

					<h2>By: <c:out value="${book.getAuthor()}" /></h2>
					
					<p><c:out value="${book.getCategory()}" /></p>
					
					<%-- publisheddate --%>
					<p>
						<strong>Published:</strong> 
						<c:out value="${book.getPublisheddate().toLocalDateTime()}" />
					</p>
					
					<%-- isbn --%>
					<p>
						<strong>ISBN:</strong> 
						<c:out value="${book.getIsbn()}" />
					</p>
					
					<%-- noofpages --%>
					<p>
						<strong>Number of Pages:</strong> 
						<c:out value="${book.getNoofpages()}" />
					</p>
					
					<%-- price --%>
					<p>
						Price: 
						<c:out value="$${book.getPrice()}" />
					</p>
					
				</td>
			</tr>
			
		</table>

		<a href="${pageContext.request.contextPath}/AdminServlet?login=1">
			Show Books </a>
			
	</div>

</body>

</html>