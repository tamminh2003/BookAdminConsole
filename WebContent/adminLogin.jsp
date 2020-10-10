<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>

<html>
<head>
<title>Admin Login</title>
<style>
#form-container {
	box-sizing: content-box;
	margin: auto;
	padding: 10px;
	width: 300px;
	border: solid thin red;
}

#submit-button {
	margin: auto;
	text-align: center;
}
</style>
</head>

<body>

	<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/bookstore" user="root"
		password="mysql" />

	<sql:query dataSource="${snapshot}" var="result">
		select isadmin from users where username = (select username from session where sessionid = ?);
 		<sql:param value="${pageContext.session.id}" />
	</sql:query>

	<c:forEach items="${result.rows}" var="r">
		<c:if test="${r.isadmin}">
			<c:redirect url="/AdminServlet" />
		</c:if>
		<c:if test="${!r.isadmin}">
			<c:redirect url="/UserServlet" />
		</c:if>
	</c:forEach>

	<div id="form-container">
		<h1>Admin Login</h1>
		<form action="Validate.jsp" method="post">
			<p><label for="uname"><b>Username</b></label> <input type="text"
					placeholder="Enter Username" name="uname" required>
			</p>

			<p><label for="psw"><b>Password</b></label> <input type="password"
					placeholder="Enter Password" name="psw" required>
			</p>

			<div id="submit-button">
				<button type="submit">Login</button>
			</div>
		</form>
	</div>

	<div align="center">
		<a href="index.jsp">Back</a>
	</div>
</body>

</html>