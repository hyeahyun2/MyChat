<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<form method="post" action="./userLogin" name="loginForm">
		<p> 아이디 : <input type="text" name="userID">
		<p> 비밀번호 : <input type="password" name="userPassword">
		<input type="submit" value="로그인">
	</form>
	<%
	// session값 검증하기 (UserRegisterServlet으로 부터 받은 값!!)
	
	String messageContent = null;
	if(session.getAttribute("messageContent") != null){
		messageContent = (String)session.getAttribute("messageContent");
	}
	
	String messageType = null;
	if(session.getAttribute("messageType") != null){
		messageType = (String)session.getAttribute("messageType");
	}
	
	%>
</body>
</html>