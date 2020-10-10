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
	// TODO Redirect to AdminServlet when already logged in
		<div id="form-container">
			<form action="Validate.jsp" method="post">
			
				<p>
					<label for="uname"><b>Username</b></label>
					<input type="text" placeholder="Enter Username" name="uname" required>
				</p>
				
				<p>
					<label for="psw"><b>Password</b></label>
					<input type="password" placeholder="Enter Password" name="psw" required>
				</p>
				
				<div id="submit-button">
					<button type="submit">Login</button>
				</div>
			</form>
		</div>
		
		<div align="center"><a href="index.jsp">Back</a></div>
	</body>
	
</html>