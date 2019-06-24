<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>

<head>
	<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
	
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta http-equiv="Expires" content="-1">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="No-Cache">
	
	<meta name="viewport" content="width=device-width, user-scalable=no">
	
	
	<title><tiles:getAsString name="title"/></title>
	
	<%@ include file="/WEB-INF/views/common/jquery.jsp"%>
	
	<link rel="stylesheet" type="text/css" media="screen" href="${ctx_res}/css/common.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="${ctx_res}/css/style.css" />
</head>

<body>
	
	<article>
		<tiles:insertAttribute name="body"/>
	</article>
	
</body>


</html>