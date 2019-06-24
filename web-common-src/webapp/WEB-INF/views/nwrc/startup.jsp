<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>Request SSO</title>
<script>
	$(document).ready(function() {
		document.loginForm.action = "${AppleIdpUrl}";;
		
		$("#loginForm").submit();
	});
</script>
</head>
<body>
	<form method = "GET" name = "loginForm" id = "loginForm">
		<input type = "hidden" id = "SAMLRequest" name = "SAMLRequest" value = "${SAMLRequest}">
		<input type = "hidden" id = "RelayState" name = "RelayState" value = "${RelayState}">
		<input type = "hidden" id = "SigAlg" name = "SigAlg" value = "${SigAlg}">
		<input type = "hidden" id = "Signature" name = "Signature" value = "${Signature}">
	</form>
</body>
</html>