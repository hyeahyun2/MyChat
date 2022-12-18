<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<script src="./js/index.js?v=<%=System.currentTimeMillis()%>" defer></script>
</head>
<body>
	<%
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String)session.getAttribute("userID");
	}
	%>
	<h1>메인 페이지 입니다</h1>
	<%
	if(userID == null){
	%>
	<ul>
		<li><a href="./login.jsp" class="goLogin">로그인</a></li>
		<li><a href="./join.jsp" class="goJoin">회원가입</a></li>
	</ul>
	<%
	}
	else {
	%>
	<ul>
		<li><a href="./chatMain.jsp" class="chatBtn">채팅목록</a></li>
		<li><a href="logoutAction.jsp">로그아웃</a></li>
		<li><a href="chat_getToID.jsp">테스트링크</a></li>
	</ul>
	<%
	}
	%>
	<script>
    	
  </script>
</body>
</html>