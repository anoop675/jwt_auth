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
		body{
			background-color:grey;
		}
		form{
			text-align:center;
			background-color:lightgrey;
			border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            width: 80%;
            max-width: 500px;
            font-family:sans-serif, monospace;
		}
		.message{
			text-align:centre;
		}
		
		button{
			height: 30px;
			width: 300px;
			background-color: rgb(45, 45, 45);
			color: white;
			font-weight: bold;
			border-radius: 20px;
			border-style: solid;
		}
		
		input{
			width: 280px;
			margin-left: 105px;
		}
		
	</style>
	
	<form action="/api/v1/auth/register" method="post">
    	<h1>Register User</h1>
    	
        <div style=margin-right:125px;>
        	<label style=margin-right:100px;>Username<br> </label>
        	<input type="text" name="username">
        </div ><br>
        <div style=margin-right:125px;>
        	<label style=margin-right:100px;>Password<br> </label>
        	<input type="text" name="password">
        </div>
        <br>
        <br>
        <div><button name="submit" type="submit" >Register</button></div>
        <br>
        <p class="message">Already Registered? <a href="/api/v1/view/login">Login here</a></p>
        <br>
    </form>
    
    
</body>
</html>