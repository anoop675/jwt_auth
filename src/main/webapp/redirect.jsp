<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!--
<!DOCTYPE html>
<html>
<head>
    <title>Redirecting</title>
</head>
<body>
    
    <script>
        // JavaScript code to generate the JWT token and set it as an Authorization header
        var jwtToken = '${jwtToken}'; // Access the JWT token passed from the Controller
        
        if (jwtToken) {
            // Set the JWT token as the Authorization header
            var headers = new Headers();
            headers.append('Authorization', 'Bearer ' + jwtToken);
            
            // Replace 'YOUR_ENDPOINT_URL' with the actual endpoint URL you want to redirect to
            var endpointUrl = '/api/v1/resource/index';
            
            // Make a redirect request with the Authorization header
            fetch(endpointUrl, {
                method: 'GET',
                headers: headers
            })
            window.location.href = endpointUrl;
            /*
            .then(function(response) {
                if (response.ok) {
                    // Redirect to the endpoint URL if the request was successful
                    //window.location.href = endpointUrl;
                } else {
                    console.error('Failed to fetch data:', response.status, response.statusText);
                }
            })
            .catch(function(error) {
                console.error('Error:', error);
            });*/
        } else {
            console.error('JWT token not available');
        }
    </script>
</body>
</html> -->
<!DOCTYPE html>
<html>
<head>
    <title>Home page</title>
</head>
<body>
	<style>
		body{
			background-color: white; 
		}
	</style>
	
	<!--
    <form id="jwtForm">
        <label for="jwtToken">JWT Token:</label>
        <input type="text" id="jwtToken" name="jwtToken">
        <button type="button" onclick="sendRequest()">Send Request</button>
    </form>-->
    
    <!--<form id="jwtForm"><button type="button" onclick="sendRequest()">Send Request</button></form>-->
    
    <div id="response"></div>
    
    <script>
        function sendRequest() {
           // var jwtToken = document.getElementById("jwtToken").value;
          
            var jwtToken = '${jwtToken}';
            
         	// Hide the form
            //document.getElementById("jwtForm").style.display = "none";
         	
         	
         	
            // Make an AJAX request with the JWT in the authorization header
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/api/v1/resource/dashboard", true);
            xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        
                    	document.getElementById("response").innerHTML = xhr.responseText;
     
                    } // Handle errors by redirecting to an error page
                    else{
                    	window.location.href = "/api/v1/view/login/error";
                    }
                }
            };
            
            xhr.send();
        }
        
     // Use window.onload to automatically call the sendRequest function when the page loads
        window.onload = sendRequest;
    </script>
</body>
</html>

