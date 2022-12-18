<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
	String userID = null;
	String toID = null;
	if(session.getAttribute("userID") != null){
		userID = (String)session.getAttribute("userID");
	}
	if(request.getParameter("toID") != null){
		toID = (String)request.getParameter("toID");
	}
	%>
<meta charset="UTF-8">
<title>채팅</title>
<link rel="stylesheet" href="./css/chatting.css">
<script src="./js/chatting.js?v=<%=System.currentTimeMillis()%>" defer></script>
</head>
<body>
	<span class="getInfo userID"><%= userID %></span>
	<span class="getInfo toID"><%= toID %></span>
	<div id="head">
		<h1 class="header"><span><%= toID %></span></h1>
		<a href="./chatMain.jsp">뒤로가기</a>
	</div>
	<div class="chat-content">
		<!-- 채팅 공간 -->
	</div>
	<input class="chat-box" id="chatInput">
	<button id="Chatsend">전송</button>
</body>
</html>