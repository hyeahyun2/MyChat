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
</head>
<body>
	<h1 class="header"><span>채팅목록</span></h1>
	<ul id="chatUserList">
		<li class="chatUser">
			<a href="./chatting.jsp?toID=test22">
		        <div></div>
		        <span>test22</span>
	    	</a>
	    </li>
	    <li class="chatUser">
	    	<a href="./chatting.jsp?toID=test11">
		        <div></div>
		        <span>test11</span>
	    	</a>
	    </li>
	    <li class="chatUser">
	    	<a href="./chatting.jsp">
		        <div></div>
		        <span>유저3</span>
	    	</a>
	    </li>
	  </ul>
</body>
</html>