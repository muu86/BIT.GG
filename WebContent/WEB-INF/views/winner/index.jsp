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
	
	<h1>팀 매치 데이터입니다</h1>
	<a href="team?a=winner">승자 그래프 보기</a>
	<a href="team?a=loser">패자 그래프 보기</a>
	<img src="images/blood.png">
	
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
			<th>firstBlood</th>
		</tr>
		<tr>
			<%-- 
			<td><%= vo.getIndex() %></td>
			 --%>
			<td><%= vo.getFirstBlood() %></td>
		</tr>
	</table>
	<% } %>
	
	
</body>
</html>