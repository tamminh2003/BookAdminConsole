<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>User Login</title>

<style>
p {
	text-align: center;
	font-weight: bold;
	color: red;
}
</style>

<script>
	function submitForm() {
		document.forms["myform"].submit();
	}
</script>
</head>

<body>

	<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/bookstore" user="root"
		password="mysql" />

	<c:set var="uname" scope="session" value="${param.uname}" />
	<c:set var="psw" scope="session" value="${param.psw}" />

	<sql:query dataSource="${snapshot}" var="result">
		select count(*) as kount from users
 		where username = ? and pass = ?;
 	<sql:param value="${uname}" />
		<sql:param value="${psw}" />
	</sql:query>

	<c:forEach items="${result.rows}" var="r">
		<c:choose>
			<c:when test="${r.kount > 0}">
				<form name="myform" action="UserServlet" method="post">
					<input type="hidden" name="action" value="userLogin">
					<input type="hidden" name="username" value="${uname}" />
				</form>
				<script>submitForm();</script>
			</c:when>

			<c:otherwise>
				<p><c:out
						value="Sorry, the username/password is incorrect 
						for ${uname}, please check your username/password." />
				</p>
			</c:otherwise>

		</c:choose>
	</c:forEach>

	<div align="center">
		<a href="Login.jsp">Back</a>
	</div>
</body>
</html>