<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<style>
		.success{
			background-color:lightgrey;
			border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            width: 80%;
            max-width: 500px;
            font-family:sans-serif, monospace;
		}
		.success-message{
			color:blue;
			text-align:center;
			font-family:sans-serif, monospace;
		}
	</style>
	<div class="success">
		<p class="success-message">Registration Successful!</p>
	</div>
	
	<jsp:include page="/UserRegister.jsp"></jsp:include>
	
</body>
</html>