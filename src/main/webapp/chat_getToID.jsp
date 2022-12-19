<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.InitialContext, javax.naming.Context, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	// userID 받아오기
	String userID = (String)session.getAttribute("userID");
	ArrayList<String> toIDList = new ArrayList<String>();
	// 객체 생성
	InitialContext initCtx = new InitialContext();
	//initCtx를 중심으로 리소스를 찾을 수 있도록
	Context envContext = (Context)initCtx.lookup("java:/comp/env");
	// 실제로 소스를 발견하게되면 우리 프로젝트에 접근할 수 있도록
	DataSource ds = (DataSource)envContext.lookup("jdbc/MyChat");
	// connection 객체를 이용해서 실제로 데이터베이스에 접근할 수 있도록
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// userID에 따른 toID 목록 불러오기
	String sql = "select toID, fromID from chat "
			+ "where (fromID = ? or toID = ?)";
	try{
		conn = ds.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userID);
		pstmt.setString(2, userID);
		rs = pstmt.executeQuery();
		while(rs.next()){ // 값 있으면
			// 중복 값 걸러내기
			if(toIDList.contains(rs.getString("toID"))) continue;
			toIDList.add(rs.getString("toID")); // 리스트에 추가
			toIDList.add(rs.getString("fromID"));
		}
	} catch(Exception e){
		e.printStackTrace();
	} finally {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
		if(initCtx != null) initCtx.close();
	}
	request.getSession().setAttribute("toIDList", toIDList);
	
	for(int i=0; i<toIDList.size(); i++){
	out.print(toIDList.get(i) + " ");
	}
	%>
</body>
</html>