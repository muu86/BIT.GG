<%@page import="gg.bit.utils.matchData.vo.ParticipantsVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소환사 정보 데이터</title>
</head>
<body>

	<jsp:include page="/pages/header.jsp" />
	
	<h1>소환사 정보</h1>
	<%
	List<ParticipantsVo> list = (List<ParticipantsVo>) request.getAttribute("list");
	%>
	
	<% for (ParticipantsVo vo: list) { %>
	<table border="1">
		<tr>
			<!--
		`	<th>index</th> 
			 -->
			<th>TeamId</th>
			<th>ChampionId</th>
			<th>Kill</th>
			<th>Death</th>
			<th>Assist</th>
			<th>Dealing</th>
		</tr>
		<tr>
			<%-- 
			<td><%= vo.getIndex() %></td>
			 --%>
			<td><%= vo.getTeamId()%></td>
			<td><%= vo.getChampionId()%></td>
			<td><%= vo.getKill()%></td>
			<td><%= vo.getDeath()%></td>
			<td><%= vo.getAssist()%></td>
			<td><%= vo.getDeal()%></td>
		</tr>
	</table>
	<% } %>
</body>
</html>