<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
	<head>
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
		<script>
		function myFunction() {
		  alert('Hello');
		}
		</script>
	</head>
	
	<body>
	
		<div id="form-container">
			<form action="Validate.jsp" method="post">
		
				<p>
					<label for="uname"><b>Username</b></label> <input type="text"
						placeholder="Enter Username" name="uname" required>
				</p>
				
				<p>
					<label for="psw"><b>Password</b></label> <input type="password"
						placeholder="Enter Password" name="psw" required>
				</p>
				
				<div id="submit-button">
					<button type="submit">Login</button>
				</div>
			</form>
		</div>
		
	</body>
	
</html>