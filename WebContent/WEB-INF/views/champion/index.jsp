<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챔피언별 승률 데이터</title>
</head>
<body>
	
	<jsp:include page="/pages/header.jsp" />
	
	<% String winRateImgName = (String) request.getAttribute("winRateImgName"); %>
	<img src=<%= "images/" + winRateImgName + ".png" %> >
	
</body>
</html>