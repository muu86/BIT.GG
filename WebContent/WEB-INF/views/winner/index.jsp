<%@page import="gg.bit.utils.matchData.vo.WinnerVo"%>
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
	<a href="winner?a=plot">그래프 보기</a>
	<%
	List<WinnerVo> list = (List<WinnerVo>) request.getAttribute("list");
	%>
	
	<% for (WinnerVo vo: list) { %>
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