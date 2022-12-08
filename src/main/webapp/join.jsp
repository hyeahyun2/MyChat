<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="./js/join.js" defer></script>
</head>
<body>
	<form action="#" method="post" name="joinForm">
		<p>아이디 : <input type="text" name="userID">
			<input type="button" value="중복확인"></p>
		<p>비밀번호 : <input type="password" name="userPassword"></p>
		<p>비밀번호 확인 : <input type="password" name="userPWCheck"></p>
		<p>이름 : <input type="text" name="userName"></p>
		<input type="button" name="submitBtn" value="가입하기">
	</form>
</body>
</html>