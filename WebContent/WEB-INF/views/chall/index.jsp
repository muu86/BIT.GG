<%@page import="gg.bit.utlils.matchData.vo.ChallengerVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린저 매치 데이터</title>
</head>
<body>
	<h1>챌린저 매치 데이터입니다</h1>
	<%
	List<ChallengerVo> list = (List<ChallengerVo>) request.getAttribute("list");
	%>
	
	<% for (ChallengerVo vo: list) { %>
	<table border="1">
		<tr>
			<!--
			<th>index</th> 
			 -->
			<th>gameId</th>
			<th>season</th>
			<th>role</th>
			<th>lane</th>
			<th>accountId</th>
		</tr>
		<tr>
			<%-- 
			<td><%= vo.getIndex() %></td>
			 --%>
			<td><%= vo.getGameId() %></td>
			<td><%= vo.getSeason() %></td>
			<td><%= vo.getRole() %></td>
			<td><%= vo.getLane() %></td>
			<td><%= vo.getAccountId() %></td>
		</tr>
	</table>
	<% } %>
</body>
</html>