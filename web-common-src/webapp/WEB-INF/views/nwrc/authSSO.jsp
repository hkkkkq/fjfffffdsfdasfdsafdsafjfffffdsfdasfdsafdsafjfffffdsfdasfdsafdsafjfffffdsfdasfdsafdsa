<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// 	boolean errTest = true;
// 	if (errTest) {
// 		response.sendError(405);
// 		return;
// 	}
%>

<html>
<head>
<title>Apple SSO Auth Complete</title>
<script>
	$(document).ready(function() {
// 		akos://web/home?appleid=${appleid}&clntip=${clntip}&nextUrl=${nextUrl}
// 		window.location.href = "akos://web/home?appleid=${appleid}&clntip=${clntip}&nextUrl=${nextUrl}";
// 		document.loginForm.action = "akos://web/home";
		document.loginForm.action = "${submitUrl}";
// 		document.loginForm.action = "${dmin}//KKOS/resources/html/apptest.html";;
		
		$("#loginForm").submit();
	});
</script>
</head>
<body>
	<form method = "get" name = "loginForm" id = "loginForm">
		<input type = "hidden" id = "clntip" name = "clntip" value = "${clntip}" ><br>
		<input type = "hidden" id = "nextUrl" name = "nextUrl" value = "${nextUrl}" ><br>
		<input type = "hidden" id = "appleid" name = "appleid" value = "${appleid}" ><br>
	</form>
</body>
</html>