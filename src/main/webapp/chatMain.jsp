<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
	<%
	String userID = null;
	ArrayList<String> toID = new ArrayList<String>();
	if(session.getAttribute("userID") != null){
		userID = (String)session.getAttribute("userID");
	}
	%>
<meta charset="UTF-8">
<title>채팅방 목록</title>
<link rel="stylesheet" href="./css/chatMain.css">
<script src="./js/chatMain.js" defer></script>
</head>
<body>
	<span class="getInfo userID"><%= userID %></span>
	<h1 class="header"><span>채팅목록</span></h1>
	<ul id="chatUserList">
		<!-- 채팅 내역 있는 user의 id 기반 채팅방 목록 -->
	</ul>
</body>
</html>