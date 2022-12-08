<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/chatting.css">
<script src="./js/chatting.js" defer></script>
</head>
<body>
<div id="head">
	<h1 class="header"><span>유저1</span></h1>
	<a href="./chatMain.jsp">뒤로가기</a>
</div>
<div class="chat-content">
	<div class="chatLine">
		<span class="chat-box">안녕?</span>
	</div>
	<div class="chatLine">
		<span class="chat-box chatMine">안녕!</span>
	</div>
</div>
<input class="chat-box" id="chatInput">
<button id="Chatsend">전송</button>
</body>
</html>