<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Failed</title>
</head>
<body>
	<style>
		.error{
			background-color:lightgrey;
			border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            width: 80%;
            max-width: 500px;
            font-family:sans-serif, monospace;
		}
		.error-type{
			color:red;
			font-weight:bold;
			text-align:center;
			font-family:sans-serif, monospace;
		}
		.error-message{
			color:red;
			text-align:center;
			font-family:sans-serif, monospace;
		}
	</style>
	<div class="error">
		<p class="error-type">Registration Failed!</p>
		<p class="error-message">The credentials entered is either used or invalid</p>
	</div>
	
	<jsp:include page="/UserRegister.jsp"></jsp:include>
	
</body>
</html>