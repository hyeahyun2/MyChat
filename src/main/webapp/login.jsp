<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="./css/login.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
	<div id="bodyWrap">
		<div id="contentWrap">
			<h1>로그인</h1>
			<form method="post" action="./userLogin" name="loginForm">
				<p> 아이디 : <input type="text" name="userID">
				<p> 비밀번호 : <input type="password" name="userPassword">
				<p><input type="submit" value="로그인">
			</form>
		</div>
	</div>
	<%--
	// session값 검증하기 (UserRegisterServlet으로 부터 받은 값!!)
	
	String messageContent = null;
	if(session.getAttribute("messageContent") != null){
		messageContent = (String)session.getAttribute("messageContent");
	}
	
	String messageType = null;
	if(session.getAttribute("messageType") != null){
		messageType = (String)session.getAttribute("messageType");
	}
	
	// messageContent값이 존재하는 경우
	if(messageContent != null) {
	--%>
	<div id="messageWrap">
		<button>닫기</button>
		<table>
			<thead>
				<tr><th>메세지</th></tr>
			</thead>
			<tbody>
				<tr><td>메세지 내용</td></tr>
			</tbody>
		</table>
	</div>
	<%--
	}
	--%>
</body>
</html>