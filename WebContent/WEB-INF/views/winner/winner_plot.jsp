<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<% String bloodImg = (String) request.getAttribute("blood_img"); %>
	<% String towerImg = (String) request.getAttribute("tower_img"); %>
	<% String inhibitorImg = (String) request.getAttribute("inhibitor_img"); %>
	<% String baronImg = (String) request.getAttribute("baron_img"); %>
	<% String riftHeraldImg = (String) request.getAttribute("riftHerald_img"); %>
	<% String dragonImg = (String) request.getAttribute("dragon_img"); %>
	
	<img src=<%= "images/" + bloodImg + ".png" %> >
	<img src=<%= "images/" + towerImg + ".png" %> >
	<img src=<%= "images/" + inhibitorImg + ".png" %> >
	<img src=<%= "images/" + baronImg + ".png" %> >
	<img src=<%= "images/" + riftHeraldImg + ".png" %> >
	<img src=<%= "images/" + dragonImg + ".png" %> >

</body>
</html>