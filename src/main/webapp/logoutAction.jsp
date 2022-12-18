<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	request.setCharacterEncoding("utf-8");
	// session 정보 지우기
	session.invalidate();
	// 메인 페이지로 이동
	response.sendRedirect("login.jsp");
	%>
</body>
</html>