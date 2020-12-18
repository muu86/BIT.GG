<%@page import="java.io.File"%>
<%@page import="gg.bit.utils.matchData.vo.TeamVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 매치 데이터</title>
</head>
<body>

	<jsp:include page="/pages/header.jsp" />
	
	<%
	List<TeamVo> list = (List<TeamVo>) request.getAttribute("list");
	%>
	
	<%
	for (TeamVo vo: list) {
	%>
	<table border="1">
		<tr>
			<!--
			<th>index</th> 
			 -->
			<th>gameId</th>
			<th>firstBlood</th>
			<th>firstTower</th>
			<th>firstInhibitor</th>
			<th>firstBaron</th>
			<th>firstRiftHerald</th>
			<th>dragonKills</th>
		</tr>
		<tr>
			<%-- 
			<td><%= vo.getIndex() %></td>
			 --%>
			<td><%= vo.getGameId() %></td> 
			<td><%= vo.getFirstBlood() %></td>
			<td><%= vo.getFirstTower() %></td>
			<td><%= vo.getFirstInhibitor() %></td>
			<td><%= vo.getFirstBaron() %></td>
			<td><%= vo.getFirstRiftHerald() %></td>
			<td><%= vo.getDragonKills() %></td>
		</tr>
	</table>
	<% } %>
	
	
</body>
</html>